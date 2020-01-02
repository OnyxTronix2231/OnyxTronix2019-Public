package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.elevator.Elevator;
import robot.elevator.MoveElevatorToHeight;
import robot.elevator.MoveElevatorToHeightAndKeep;
import robot.elevator.StopElevatorCommands;
import robot.pitch.MovePitchToAngle;
import robot.pitch.Pitch;
import robot.pitch.PitchConstants;

import java.util.function.Supplier;

public class FlipAndMoveToLevel extends CommandGroup {
  public FlipAndMoveToLevel(final Pitch pitch, final Elevator elevator, final LevelControl levelControl,
                            final Supplier<Number> height, final Supplier<Number> angle) {
    addSequential(new StartMoveToLevel(levelControl, angle, height));
    addParallel(new MoveElevatorToHeightAndKeep(elevator, elevator::getCurrentHeight));
    addParallel(new MovePitchToSafeAngleAndKeepByLevel(pitch, angle, levelControl, levelControl::getElevatorHeight, false));
    addSequential(new WaitForPitchSafeByMaxLevel(levelControl, levelControl::getPitchAngle, levelControl::getElevatorHeight));

    addParallel(new ChooseHeightByFlipping(elevator, levelControl, height));
    addSequential(new ActivateIfFlipping(levelControl, new WaitForFlipLevel(levelControl)));
    addParallel(new MovePitchToSafeAngleAndKeepByLevel(pitch, angle, levelControl, height, true));
    addSequential(new WaitForPitchSafeByMaxLevel(levelControl, angle, height));

    addSequential(new MoveElevatorToHeight(elevator, height));
    addParallel(new MoveElevatorToHeightAndKeep(elevator, height));
    addSequential(new MovePitchToAngle(pitch, angle, PitchConstants.MAX_ERROR));
    addSequential(new StopElevatorCommands(elevator));
  }
}
/*
-MovePitchToSafeAngleAndKeepByLevel and WaitForPitchSafeByMaxLevel:
 Safe angle range is determined by the current height and side.

-"Flipping" is also when the current side IS NOT rear or front so the "mids" areas counts as flipping unless
 the current height is less then the min safe height.

-"Activate" commands are conditional command checking booleans stored in levelControl subsystem

-WaitForPitchSafeByTargetLevel:
 Safe angle range is determined by the target height and side.

-Last 4 commands are to make sure both the pitch and elevator are finished cause we cant know who will be faster
 so doing only:

    addParallel(new MoveElevatorToHeightAndKeep(elevator, height));
    addSequential(new MovePitchToAngle(pitch, angle, PitchConstants.MAX_ERROR));
    addSequential(new StopElevatorCommands(elevator));

 can cause the elevator not reaching is final target and getting stopped before (and vice versa with pitch)

-MoveElevatorToSafeFlippingHeightByHeight:
    Moves to the closest flipping height by the target height.

-MovePitchToAngleAndKeepByCurrentAndTargetHeight:
 constantly updating the target angle by the current and target height.
 */
package robot.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.GameConstants;
import robot.elevator.Elevator;

public class DownFromPlatform extends CommandGroup {
  public DownFromPlatform(final DriveTrain driveTrain, final Elevator elevator, final double maxPitchAngle,
                          final double endingAngle, final double tolerance) {
    addParallel(new DriveByDistance(driveTrain, GameConstants.SECOND_FLOOR_LENGTH
        * GameConstants.METER_TO_CM));
    addSequential(new WaitUntilOnAngle(driveTrain, maxPitchAngle, tolerance));
    addSequential(new WaitUntilOnAngle(driveTrain, endingAngle, tolerance));
    addSequential(new StopDriving(driveTrain, elevator));
  }
}

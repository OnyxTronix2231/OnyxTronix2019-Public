package robot.climber;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import robot.climber.lift.MoveLiftByHeight;
import robot.climber.lift.RobotLift;
import robot.climber.pistons.ClimberPistons;
import robot.climber.pistons.OpenClimberPistons;
import robot.climber.trainwheel.MoveTrainForward;
import robot.climber.trainwheel.TrainWheel;
import robot.drivetrain.DriveBySpeed;
import robot.drivetrain.DriveTrain;
import robot.elevator.Elevator;

public class ClimbLevelByHeight extends CommandGroup {
  private static final double REMAINING_STAIR_LENGTH = 70;
  private static final double START_HEIGHT_ROBOT = -2;
  private static final double DRIVE_SPEED_FORWARD = -0.6;
  private static final double DRIVE_SPEED_ROTATION = 0;
  private static final int AMOUNT_TO_REDUCE = 5;

  public ClimbLevelByHeight(final RobotLift robotLift, final TrainWheel trainWheel, final DriveTrain driveTrain,
                            final Elevator elevator, final double height, final ClimberPistons climberPistons) {
    addSequential(new MoveLiftByHeight(robotLift, () -> height));
    addSequential(new MoveTrainForward(trainWheel));
    addSequential(new MoveLiftByHeight(robotLift, () -> (height - AMOUNT_TO_REDUCE)));
    addParallel(new DriveBySpeed(driveTrain, () -> DRIVE_SPEED_FORWARD, () -> DRIVE_SPEED_ROTATION, elevator), 2);
    addParallel(new MoveLiftByHeight(robotLift, () -> START_HEIGHT_ROBOT));
    addSequential(new WaitCommand(2));
    addSequential(new OpenClimberPistons(climberPistons));
  }
}

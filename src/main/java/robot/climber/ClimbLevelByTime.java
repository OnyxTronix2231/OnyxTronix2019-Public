package robot.climber;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.climber.lift.LowerLift;
import robot.climber.lift.MoveLiftBySpeed;
import robot.climber.lift.RaiseLift;
import robot.climber.lift.RobotLift;
import robot.climber.trainwheel.MoveTrainWheelBySpeed;
import robot.climber.trainwheel.TrainWheel;
import robot.drivetrain.DriveBySpeed;
import robot.drivetrain.DriveTrain;
import robot.elevator.Elevator;

public class ClimbLevelByTime extends CommandGroup {
  private static final double MOVE_LIFT_WHEEL_TIMEOUT = 10;
  private static final double KEEP_LIFT_SPEED = 0.7;
  private static final double DRIVE_SPEED = 0.4;
  private static final double MAX_SPEED = 1;

  public ClimbLevelByTime(final RobotLift robotLift, final TrainWheel trainWheel, final DriveTrain driveTrain,
                          final Elevator elevator, final int timeToClimb) {
    addSequential(new LowerLift(robotLift), timeToClimb);
    addParallel(new MoveLiftBySpeed(robotLift, KEEP_LIFT_SPEED), MOVE_LIFT_WHEEL_TIMEOUT);
    addParallel(new DriveBySpeed(driveTrain, () -> DRIVE_SPEED, () -> 0, elevator), MOVE_LIFT_WHEEL_TIMEOUT);
    addSequential(new MoveTrainWheelBySpeed(trainWheel, MAX_SPEED), MOVE_LIFT_WHEEL_TIMEOUT);
    addSequential(new RaiseLift(robotLift), timeToClimb);
    addSequential(new DriveBySpeed(driveTrain, () -> DRIVE_SPEED, () -> 0, elevator));
  }
}

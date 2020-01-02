package robot.climber;

import robot.climber.lift.RobotLift;
import robot.climber.trainwheel.TrainWheel;
import robot.drivetrain.DriveTrain;
import robot.elevator.Elevator;

public class ClimbLevelThreeOnlyByTime extends ClimbLevelByTime {
  private static final int LEVEL_THREE_TIMEOUT = 6;

  public ClimbLevelThreeOnlyByTime(final RobotLift robotLift, final TrainWheel trainWheel, final DriveTrain driveTrain,
                                   final Elevator elevator) {
    super(robotLift, trainWheel, driveTrain, elevator, LEVEL_THREE_TIMEOUT);
  }
}

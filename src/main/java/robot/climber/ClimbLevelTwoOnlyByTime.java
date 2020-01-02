package robot.climber;

import robot.climber.lift.RobotLift;
import robot.climber.trainwheel.TrainWheel;
import robot.drivetrain.DriveTrain;
import robot.elevator.Elevator;

public class ClimbLevelTwoOnlyByTime extends ClimbLevelByTime {
  private static final int LEVEL_TWO_TIMEOUT = 3;

  public ClimbLevelTwoOnlyByTime(final RobotLift robotLift, final TrainWheel trainWheel, final DriveTrain driveTrain,
                                 final Elevator elevator) {
    super(robotLift, trainWheel, driveTrain, elevator, LEVEL_TWO_TIMEOUT);
  }
}

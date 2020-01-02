package robot.climber;

import robot.climber.lift.RobotLift;
import robot.climber.pistons.ClimberPistons;
import robot.climber.trainwheel.TrainWheel;
import robot.drivetrain.DriveTrain;
import robot.elevator.Elevator;

public class ClimbLevelTwo extends ClimbLevelByHeight {
  private static final double LEVEL_THREE_HEIGHT = 30;

  public ClimbLevelTwo(final RobotLift robotLift, final TrainWheel trainWheel, final DriveTrain driveTrain,
                       final Elevator elevator, final ClimberPistons climberPistons) {
    super(robotLift, trainWheel, driveTrain, elevator, LEVEL_THREE_HEIGHT, climberPistons);
  }
}

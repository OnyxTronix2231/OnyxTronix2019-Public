package robot.climber;

import robot.climber.lift.MoveLiftByHeight;
import robot.climber.lift.RobotLift;

public class RaiseSecondRobot extends MoveLiftByHeight {
  private static final double START_HEIGHT_ROBOT = 0;

  public RaiseSecondRobot(final RobotLift robotLift) {
    super(robotLift, () -> START_HEIGHT_ROBOT);
  }
}

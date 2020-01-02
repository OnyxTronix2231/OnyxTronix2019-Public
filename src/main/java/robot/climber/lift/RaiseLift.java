package robot.climber.lift;

public class RaiseLift extends MoveLiftBySpeed {
  private static final double SPEED = 1;

  public RaiseLift(final RobotLift robotLift) {
    super(robotLift, SPEED);
  }
}

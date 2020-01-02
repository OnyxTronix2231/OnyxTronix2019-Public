package robot.climber.lift;

public class LowerLift extends MoveLiftBySpeed {
  private static final double SPEED = -1;
  private final RobotLift robotLift;

  public LowerLift(final RobotLift robotLift) {
    super(robotLift, SPEED);
    this.robotLift = robotLift;
  }

  @Override
  protected boolean isFinished() {
    return robotLift.getMicroSwitchValueReverse();
  }

}

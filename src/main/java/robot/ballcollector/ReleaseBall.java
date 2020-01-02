package robot.ballcollector;

public class ReleaseBall extends MoveCollectorBySpeed {
  private static final double MAX_SPEED = -0.35;

  public ReleaseBall(final BallCollector ballCollector) {
    super(ballCollector, MAX_SPEED);
  }
}

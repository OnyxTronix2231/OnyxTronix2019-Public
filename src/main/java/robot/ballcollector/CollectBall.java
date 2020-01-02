package robot.ballcollector;

public class CollectBall extends MoveCollectorBySpeed {
  private static final double MAX_SPEED = 1;

  public CollectBall(final BallCollector ballCollector) {
    super(ballCollector, MAX_SPEED);
  }
}

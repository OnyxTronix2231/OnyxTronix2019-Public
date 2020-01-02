package robot.ballcollector;

import edu.wpi.first.wpilibj.command.Command;

public class MoveCollectorBySpeed extends Command {
  protected final BallCollector ballCollector;
  private final double ballCollectorSpeed;

  public MoveCollectorBySpeed(final BallCollector ballCollector, final double ballCollectorSpeed) {
    this.ballCollectorSpeed = ballCollectorSpeed;
    this.ballCollector = ballCollector;
    requires(ballCollector);
  }

  @Override
  protected void execute() {
    ballCollector.collectBallBySpeed(ballCollectorSpeed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    ballCollector.collectBallBySpeed(0);
  }
}

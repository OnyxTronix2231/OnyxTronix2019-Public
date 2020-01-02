package robot.ballcollector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WaitUntilBallCollectedByAmp extends CommandGroup {
  private final BallCollector ballCollector;

  public WaitUntilBallCollectedByAmp(final BallCollector ballCollector) {
    this.ballCollector = ballCollector;
  }

  @Override
  protected boolean isFinished() {
    return ballCollector.isBallCollectedByAmp();
  }
}

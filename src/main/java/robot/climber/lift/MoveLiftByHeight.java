package robot.climber.lift;

import java.util.function.Supplier;

public class MoveLiftByHeight extends MoveLiftByHeightAndKeep {
  private static final double NUMBER_OF_SUCCESSFUL_CHECKS = 10;
  private static final int RESET_COUNTER = 0;
  private boolean isFinished;
  private int count;

  public MoveLiftByHeight(final RobotLift robotLift, final Supplier<Number> height) {
    super(robotLift, height);
  }

  @Override
  protected void initialize() {
    super.initialize();
    isFinished = false;
    count = RESET_COUNTER;
  }

  @Override
  protected void execute() {
    super.execute();
    checkIsFinished(robotLift.isOnTarget());
  }

  protected void checkIsFinished(boolean isOnTarget) {
    if (isOnTarget) {
      if (count >= NUMBER_OF_SUCCESSFUL_CHECKS) {
        count = RESET_COUNTER;
        isFinished = true;
      }
      count++;
    }
  }

  @Override
  protected boolean isFinished() {
    return isFinished;
  }
}

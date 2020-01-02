package robot.elevator;

import java.util.function.Supplier;

public class MoveElevatorToHeight extends MoveElevatorToHeightAndKeep {
  private static final double NUMBER_OF_SUCCESSFUL_CHECKS = 5;
  private static final int RESET_COUNTER = 0;
  private boolean isFinished;
  private int count;

  public MoveElevatorToHeight(final Elevator elevator, final Supplier<Number> height) {
    super(elevator, height);
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
    checkIsFinished(elevator.isOnTarget());
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

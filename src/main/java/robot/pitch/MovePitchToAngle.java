package robot.pitch;

import java.util.function.Supplier;

public class MovePitchToAngle extends MovePitchToAngleAndKeep {
  private static final double NUMBER_OF_SUCCESSFUL_CHECKS = 10;
  private static final int RESET_COUNTER = 0;
  private final double maxError;
  private boolean isFinished;
  private int counter;

  public MovePitchToAngle(final Pitch pitch, final Supplier<Number> angle, final double maxError) {
    super(pitch, angle);
    this.maxError = maxError;
    requires(pitch);
  }

  @Override
  protected void initialize() {
    isFinished = false;
    counter = RESET_COUNTER;
  }

  @Override
  protected void execute() {
    pitch.moveToAngleByMotionMagic(getAngle.get().doubleValue());
    if (pitch.isPitchOnAngle(maxError)) {
      if (counter >= NUMBER_OF_SUCCESSFUL_CHECKS) {
        counter = RESET_COUNTER;
        isFinished = true;
      }
      counter++;
    }
  }

  @Override
  protected boolean isFinished() {
    return isFinished;
  }
}

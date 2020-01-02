package robot.levelcontrol;

import robot.pitch.MovePitchToAngleAndKeep;
import robot.pitch.Pitch;

import java.util.function.Supplier;

public class MovePitchToSafeAngleAndKeepByLevel extends MovePitchToAngleAndKeep {
  private final LevelControl levelControl;
  private final boolean canFlip;
  private final Supplier<Number> getHeight;

  public MovePitchToSafeAngleAndKeepByLevel(final Pitch pitch, final Supplier<Number> getAngle,
                                            final LevelControl levelControl, final Supplier<Number> getHeight,
                                            final boolean canFlip) {
    super(pitch, getAngle);
    this.levelControl = levelControl;
    this.getHeight = getHeight;
    this.canFlip = canFlip;
  }

  @Override
  protected void initialize() {
    super.initialize();
    angle = levelControl.getCurrentSafeAngleByTargetAngle(angle, canFlip, getHeight.get().doubleValue());
  }
}

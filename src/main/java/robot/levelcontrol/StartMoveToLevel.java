package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.InstantCommand;

import java.util.function.Supplier;

public class StartMoveToLevel extends InstantCommand {
  private final LevelControl levelControl;
  private final Supplier<Number> getHeight;
  private final Supplier<Number> getAngle;

  public StartMoveToLevel(final LevelControl levelControl, final Supplier<Number> getAngle, final Supplier<Number> getHeight) {
    this.levelControl = levelControl;
    this.getHeight = getHeight;
    this.getAngle = getAngle;
  }

  @Override
  protected void initialize() {
    levelControl.setIsPitchFlipping(getAngle.get().doubleValue(), getHeight.get().doubleValue());
  }
}

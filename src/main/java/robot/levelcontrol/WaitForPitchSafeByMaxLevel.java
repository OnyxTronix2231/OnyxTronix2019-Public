package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class WaitForPitchSafeByMaxLevel extends Command {
  private final LevelControl levelControl;
  private Supplier<Number> getAngle;
  private Supplier<Number> getHeight;
  private double maxAngle;

  public WaitForPitchSafeByMaxLevel(final LevelControl levelControl, Supplier<Number> getAngle, Supplier<Number> getHeight) {
    this.levelControl = levelControl;
    this.getAngle = getAngle;
    this.getHeight = getHeight;
  }

  @Override
  protected void initialize() {
    maxAngle = levelControl.getMaxAngleByLevel(getAngle.get().doubleValue(), getHeight.get().doubleValue());
  }

  @Override
  protected boolean isFinished() {
    if (levelControl.isAngleFront(getAngle.get().doubleValue())) {
      return maxAngle > levelControl.getPitchAngle() - 1;
    } else {
      return maxAngle < levelControl.getPitchAngle() + 1;
    }
  }
}

package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForFlipLevel extends Command {
  private final LevelControl levelControl;

  public WaitForFlipLevel(final LevelControl levelControl) {
    this.levelControl = levelControl;
  }

  @Override
  protected boolean isFinished() {
    return levelControl.isInFlippingHeight();
  }
}

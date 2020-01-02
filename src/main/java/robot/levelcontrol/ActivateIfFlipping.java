package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class ActivateIfFlipping extends ConditionalCommand {
  private final LevelControl levelControl;

  public ActivateIfFlipping(final LevelControl levelControl, final Command command) {
    super(command);
    this.levelControl = levelControl;
  }

  @Override
  protected boolean condition() {
    return levelControl.isPitchFlipping();
  }
}

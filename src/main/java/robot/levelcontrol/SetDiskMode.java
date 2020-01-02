package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.InstantCommand;
import robot.levelcontrol.constants.LevelConstants;

public class SetDiskMode extends InstantCommand {
  @Override
  protected void initialize() {
    LevelConstants.BallOrDiskMode.setDiskMode(true);
    LevelConstants.BallOrDiskMode.setBallMode(false);
  }
}

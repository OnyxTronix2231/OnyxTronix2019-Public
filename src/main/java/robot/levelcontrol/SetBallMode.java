package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.InstantCommand;
import robot.levelcontrol.constants.LevelConstants;

public class SetBallMode extends InstantCommand {
  @Override
  protected void initialize() {
    LevelConstants.BallOrDiskMode.setBallMode(true);
    LevelConstants.BallOrDiskMode.setDiskMode(false);
  }
}

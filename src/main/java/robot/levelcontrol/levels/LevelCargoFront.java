package robot.levelcontrol.levels;

import robot.elevator.Elevator;
import robot.levelcontrol.FlipAndMoveToLevel;
import robot.levelcontrol.LevelControl;
import robot.levelcontrol.constants.LevelParameters;
import robot.pitch.Pitch;

public class LevelCargoFront extends FlipAndMoveToLevel {
  public LevelCargoFront(final Pitch pitch, final Elevator elevator, final LevelControl levelControl, final LevelParameters levelHeight, final LevelParameters levelAngle) {
    super(pitch, elevator, levelControl, levelHeight::getFrontLevelCargoBall,
        levelAngle::getFrontLevelCargoBall);
  }
}

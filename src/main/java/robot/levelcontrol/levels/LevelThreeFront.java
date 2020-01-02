package robot.levelcontrol.levels;

import robot.diskcollector.DiskCollector;
import robot.elevator.Elevator;
import robot.levelcontrol.FlipAndMoveToLevel;
import robot.levelcontrol.LevelControl;
import robot.levelcontrol.constants.LevelParameters;
import robot.pitch.Pitch;

public class LevelThreeFront extends FlipAndMoveToLevel {
  public LevelThreeFront(final Pitch pitch, final Elevator elevator, final LevelControl levelControl,
                         final DiskCollector diskCollector, final LevelParameters levelHeight, final LevelParameters levelAngle) {
    super(pitch, elevator, levelControl, () -> diskCollector.chooseByGamePiece(
        levelHeight.getFrontLevelThreeDisk(), levelHeight.getFrontLevelThreeBall()),
        () -> diskCollector.chooseByGamePiece(levelAngle.getFrontLevelThreeDisk(), levelAngle.getFrontLevelThreeBall()));
  }
}

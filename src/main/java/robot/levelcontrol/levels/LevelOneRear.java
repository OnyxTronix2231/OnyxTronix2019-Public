package robot.levelcontrol.levels;

import robot.diskcollector.DiskCollector;
import robot.elevator.Elevator;
import robot.levelcontrol.FlipAndMoveToLevel;
import robot.levelcontrol.LevelControl;
import robot.levelcontrol.constants.LevelParameters;
import robot.pitch.Pitch;

public class LevelOneRear extends FlipAndMoveToLevel {
  public LevelOneRear(final Pitch pitch, final Elevator elevator, final LevelControl levelControl,
                      final DiskCollector diskCollector, final LevelParameters levelHeight, final LevelParameters levelAngle) {
    super(pitch, elevator, levelControl, () -> diskCollector.chooseByGamePiece(
        levelHeight.getRearLevelOneDisk(), levelHeight.getRearLevelOneBall()),
        () -> diskCollector.chooseByGamePiece(levelAngle.getRearLevelOneDisk(), levelAngle.getRearLevelOneBall()));
  }
}

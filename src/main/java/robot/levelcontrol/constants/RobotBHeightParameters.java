package robot.levelcontrol.constants;

import static robot.elevator.ElevatorConstants.OFFSET_FROM_FLOOR;

public class RobotBHeightParameters implements LevelParameters {
  @Override
  public double getFrontLevelOneBall() {
    return OFFSET_FROM_FLOOR;
  }

  @Override
  public double getFrontLevelTwoBall() {
    return 90;
  }

  @Override
  public double getFrontLevelThreeBall() {
    return 160;
  }

  @Override
  public double getFrontLevelCargoBall() {
    return 62;
  }

  @Override
  public double getRearLevelOneBall() {
    return OFFSET_FROM_FLOOR;
  }

  @Override
  public double getRearLevelTwoBall() {
    return 150;
  }

  @Override
  public double getRearLevelThreeBall() {
    return 155;
  }

  @Override
  public double getRearLevelCargoBall() {
    return 100;
  }

  @Override
  public double getFrontLevelOneDisk() {
    return OFFSET_FROM_FLOOR;
  }

  @Override
  public double getFrontLevelTwoDisk() {
    return 102;
  }

  @Override
  public double getFrontLevelThreeDisk() {
    return 167;
  }

  @Override
  public double getRearLevelOneDisk() {
    return OFFSET_FROM_FLOOR;
  }

  @Override
  public double getRearLevelTwoDisk() {
    return 105;
  }

  @Override
  public double getRearLevelThreeDisk() {
    return 155;
  }
}

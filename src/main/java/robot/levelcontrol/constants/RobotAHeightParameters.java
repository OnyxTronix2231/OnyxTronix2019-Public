package robot.levelcontrol.constants;

import static robot.elevator.ElevatorConstants.OFFSET_FROM_FLOOR;

public class RobotAHeightParameters implements LevelParameters {
  @Override
  public double getFrontLevelOneBall() {
    return OFFSET_FROM_FLOOR;
  }

  @Override
  public double getFrontLevelTwoBall() {
    return 99;
  }

  @Override
  public double getFrontLevelThreeBall() {
    return 165;
  }

  @Override
  public double getFrontLevelCargoBall() {
    return 64;
  }

  @Override
  public double getRearLevelOneBall() {
    return getFrontLevelOneBall();
  }


  @Override
  public double getRearLevelTwoBall() {
    return 144;
  }

  @Override
  public double getRearLevelThreeBall() {
    return 166;
  }

  @Override
  public double getRearLevelCargoBall() {
    return 115;
  }

  @Override
  public double getFrontLevelOneDisk() {
    return getFrontLevelOneBall();
  }

  @Override
  public double getFrontLevelTwoDisk() {
    return getFrontLevelTwoBall();
  }

  @Override
  public double getFrontLevelThreeDisk() {
    return getFrontLevelThreeBall();
  }

  @Override
  public double getRearLevelOneDisk() {
    return OFFSET_FROM_FLOOR;
  }

  @Override
  public double getRearLevelTwoDisk() {
    return 99;
  }

  @Override
  public double getRearLevelThreeDisk() {
    return 165;
  }
}

package robot.levelcontrol.constants;

public class RobotAAngleParameters implements LevelParameters {
  @Override
  public double getFrontLevelOneBall() {
    return 0;
  }

  @Override
  public double getFrontLevelTwoBall() {
    return 0;
  }

  @Override
  public double getFrontLevelThreeBall() {
    return 0;
  }

  @Override
  public double getFrontLevelCargoBall() {
    return 0;
  }

  @Override
  public double getRearLevelOneBall() {
    return 180;
  }

  @Override
  public double getRearLevelTwoBall() {
    return 180;
  }

  @Override
  public double getRearLevelThreeBall() {
    return 143;
  }

  @Override
  public double getRearLevelCargoBall() {
    return 180;
  }

  @Override
  public double getFrontLevelOneDisk() {
    return getFrontLevelOneBall();
  }

  @Override
  public double getFrontLevelTwoDisk() {
    return 4;
  }

  @Override
  public double getFrontLevelThreeDisk() {
    return 10;
  }

  @Override
  public double getRearLevelOneDisk() {
    return getRearLevelOneBall();
  }

  @Override
  public double getRearLevelTwoDisk() {
    return getRearLevelTwoBall();
  }

  @Override
  public double getRearLevelThreeDisk() {
    return 175;
  }
}

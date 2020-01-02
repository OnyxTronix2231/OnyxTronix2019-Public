package robot.levelcontrol.constants;

public class RobotBAngleParameters implements LevelParameters {
  @Override
  public double getFrontLevelOneBall() {
    return 0;
  }

  @Override
  public double getFrontLevelTwoBall() {
    return 5;
  }

  @Override
  public double getFrontLevelThreeBall() {
    return 5;
  }

  @Override
  public double getFrontLevelCargoBall() {
    return 0;
  }

  @Override
  public double getRearLevelOneBall() {
    return 185;
  }

  @Override
  public double getRearLevelTwoBall() {
    return 185;
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
    return 0;
  }

  @Override
  public double getFrontLevelTwoDisk() {
    return 7;
  }

  @Override
  public double getFrontLevelThreeDisk() {
    return 15;
  }

  @Override
  public double getRearLevelOneDisk() {
    return 180;
  }

  @Override
  public double getRearLevelTwoDisk() {
    return 185;
  }

  @Override
  public double getRearLevelThreeDisk() {
    return 185;
  }
}

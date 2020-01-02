package robot.levelcontrol.constants;

public class LevelConstants {
  public static class BallOrDiskMode {
    private static boolean isBallMode = false;
    private static boolean isDiskMode = true;

    public static boolean isDiskMode() {
      return isDiskMode;
    }

    public static void setDiskMode(boolean diskMode) {
      isDiskMode = diskMode;
    }

    public static boolean isBallMode() {
      return isBallMode;
    }

    public static void setBallMode(boolean ballMode) {
      isBallMode = ballMode;
    }
  }
}

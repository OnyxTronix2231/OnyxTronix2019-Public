package robot;

public class GameConstants {
  // All distances are in m
  public static final double METER_TO_CM = 100;
  public static final double SECOND_FLOOR_LENGTH = 1.2192;
  public static final double PLATFORM_LENGTH = 1.226312;
  public static final double PLATFORM_TO_ROCKET_ANGLE = 43.79;
  public static final double PLATFORM_TO_ROCKET_DISTANCE = 2.15;
  public static final double ROCKET_TO_FEEDER_REAR_ANGLE = 48.3779;
  public static final double ROCKET_TO_FEEDER_CENTER_LINE = 0.42;
  public static final double ROCKET_TO_FEEDER_DISTANCE = 3.55;
  public static final double FEEDER_TO_ROCKET_TURN_POINT = (ROCKET_TO_FEEDER_DISTANCE
      - ((ROCKET_TO_FEEDER_CENTER_LINE * Math.sin(90 - ROCKET_TO_FEEDER_REAR_ANGLE) / 0.894)));
}

package robot;

public class TargetPose {
  private final double distance;
  private final double angle;
  private final double orientation;

  public TargetPose(double distance, double angle, double orientation){
    this.distance = distance;
    this.angle = angle;
    this.orientation = orientation;
  }

  public double getDistance() {
    return distance;
  }

  public double getAngle() {
    return angle;
  }

  public double getOrientation() {
    return orientation;
  }
}
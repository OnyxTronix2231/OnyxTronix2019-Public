package robot.levelcontrol;

import robot.elevator.Elevator;
import robot.pitch.Pitch;

public class LevelControl {
  private static final int LEVEL_AMOUNT = 3;
  private static final double[] ELEVATOR_HEIGHTS = new double[LEVEL_AMOUNT];
  private static final double[] PITCH_MIN_REAR_ANGLES = new double[LEVEL_AMOUNT];
  private static final double[] PITCH_MAX_FRONT_ANGLES = new double[LEVEL_AMOUNT];
  private static final double PITCH_MIDDLE_ANGLE = 90;
  private static final double ELEVATOR_MIN_SAFE_FLIPPING_HEIGHT = 80;
  private static final double ELEVATOR_MAX_SAFE_FLIPPING_HEIGHT = 180;
  private final Elevator elevator;
  private final Pitch pitch;
  private boolean isFlipping;

  public LevelControl(final Pitch pitch, final Elevator elevator) {
    this.elevator = elevator;
    this.pitch = pitch;

    ELEVATOR_HEIGHTS[0] = 0;
    ELEVATOR_HEIGHTS[1] = ELEVATOR_MIN_SAFE_FLIPPING_HEIGHT;
    ELEVATOR_HEIGHTS[2] = ELEVATOR_MAX_SAFE_FLIPPING_HEIGHT;

    PITCH_MIN_REAR_ANGLES[0] = 110;
    PITCH_MIN_REAR_ANGLES[1] = 0;
    PITCH_MIN_REAR_ANGLES[2] = 90;

    PITCH_MAX_FRONT_ANGLES[0] = 2;
    PITCH_MAX_FRONT_ANGLES[1] = 180;
    PITCH_MAX_FRONT_ANGLES[2] = 90;

    isFlipping = false;
  }

  public double getMaxAngleByLevel(final double angle, final double height) {
    if (isAngleFront(angle)) {
      return getAngleByLevel(PITCH_MAX_FRONT_ANGLES, getLevelByHeight(height));
    } else {
      return getAngleByLevel(PITCH_MIN_REAR_ANGLES, getLevelByHeight(height));
    }
  }

  public double getCurrentSafeAngleByTargetAngle(double targetAngle, boolean canFlip, final double height) {
    double angle;
    double angleReference;
    if (!canFlip) {
      angleReference = getPitchAngle();
      angle = getMaxAngleByLevel(angleReference, height);
    } else {
      angleReference = targetAngle;
      angle = getMaxAngleByLevel(angleReference, height);
    }
    if (isAngleFront(angleReference)) {
      angle = Math.min(targetAngle, angle);
    } else {
      angle = Math.max(targetAngle, angle);
    }
    return angle;
  }

  public void setIsPitchFlipping(final double angle, final double height) {
    if (getLevelByHeight(height) != getLevelByHeight(getElevatorHeight()) || isAngleFront(angle) != isAngleFront(getPitchAngle())) {
      setIsFlipping(true);
    } else {
      setIsFlipping(false);
    }
  }

  public Side getAngleSideByAngleAndHeight(final double angle, final double height) {
    if (angle > getAngleByLevel(PITCH_MAX_FRONT_ANGLES, getLevelByHeight(height))) {
      if (angle < getAngleByLevel(PITCH_MIN_REAR_ANGLES, getLevelByHeight(height))) {
        if (isAngleFront(angle)) {
          return Side.MIDDLE_FRONT;
        } else {
          return Side.MIDDLE_REAR;
        }
      } else {
        return Side.REAR;
      }
    } else {
      return Side.FRONT;
    }
  }

  public boolean isInFlippingHeight() {
    return getElevatorHeight() + 2 > ELEVATOR_MIN_SAFE_FLIPPING_HEIGHT && getElevatorHeight() - 2 < ELEVATOR_MAX_SAFE_FLIPPING_HEIGHT;
  }

  public double getElevatorSafeHeight(final double height) {
    if (height < ELEVATOR_MIN_SAFE_FLIPPING_HEIGHT) {
      return ELEVATOR_MIN_SAFE_FLIPPING_HEIGHT;
    } else if (height > ELEVATOR_MAX_SAFE_FLIPPING_HEIGHT) {
      return ELEVATOR_MAX_SAFE_FLIPPING_HEIGHT;
    } else {
      return height;
    }
  }

  public boolean isPitchFlipping() {
    return isFlipping;
  }

  public double getElevatorHeight() {
    return elevator.getCurrentHeight();
  }

  public double getPitchAngle() {
    return pitch.getCurrentAngle();
  }

  public boolean isAngleFront(final double angle) {
    return angle < PITCH_MIDDLE_ANGLE;
  }

  private int getLevelByHeight(double height) {
    if (height < 0) {
      return 0;
    }

    for (int i = 0; i < ELEVATOR_HEIGHTS.length - 2; i++) {
      if (height > ELEVATOR_HEIGHTS[i] && height < ELEVATOR_HEIGHTS[i + 1]) {
        return i;
      }
    }
    return ELEVATOR_HEIGHTS.length - 1;
  }

  private void setIsFlipping(final boolean isFlipping) {
    this.isFlipping = isFlipping;
  }

  private double getAngleByLevel(final double[] angleGroup, final int level) {
    return angleGroup[level];
  }

  public enum Side {
    FRONT, REAR, MIDDLE_FRONT, MIDDLE_REAR
  }
}

package robot.drivetrain.paths;

import robot.TargetPose;
import robot.drivetrain.DriveByDistance;
import robot.drivetrain.DriveTrain;

import java.util.function.Supplier;

import static robot.drivetrain.DriveTrain.METER_TO_ENCODER_UNITS;

public class DriveByDynamicPath extends DriveByDistance {
  private final Supplier<TargetPose> getTarget;
  private final int direction;
  private final DriveTrain driveTrain;
  public static final int MAIN_PID_SLOT = 1;
  public static final int AUX_PID_SLOT = 2;
  private final DynamicMotionProfileGenerator dynamicPathGenerator;

  public DriveByDynamicPath(final DriveTrain driveTrain, final Supplier<TargetPose> getTarget,
                            final int direction, DynamicMotionProfileGenerator dynamicPathGenerator) {
    super(driveTrain, 0);
    this.driveTrain = driveTrain;
    this.getTarget = getTarget;
    this.direction = direction;
    this.dynamicPathGenerator = dynamicPathGenerator;
    requires(driveTrain);
  }

  @Override
  protected void initialize() {
    TargetPose target = getTarget.get();
    isFinished = false;
    numberOfSuccessfulChecks = 0;
    if (direction == 1) {
      driveTrain.driveByMotionProfile(
          dynamicPathGenerator.generateRightBuffer(target, direction, METER_TO_ENCODER_UNITS, MAIN_PID_SLOT, AUX_PID_SLOT),
          dynamicPathGenerator.generateLeftBuffer(target, direction, METER_TO_ENCODER_UNITS, MAIN_PID_SLOT, AUX_PID_SLOT));
    } else {
      driveTrain.driveByMotionProfile(
          dynamicPathGenerator.generateLeftBuffer(target, direction, METER_TO_ENCODER_UNITS, MAIN_PID_SLOT, AUX_PID_SLOT),
          dynamicPathGenerator.generateRightBuffer(target, direction, METER_TO_ENCODER_UNITS, MAIN_PID_SLOT, AUX_PID_SLOT));
    }
  }
}


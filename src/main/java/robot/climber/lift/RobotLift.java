package robot.climber.lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RobotLift extends Subsystem {
  private static final double KEEP_SPEED = 0.2;
  private static final double ENCODER_ERROR = 100;
  private static final double ENCODER_UNIT = 3.5 * Math.PI;
  private static final int ENCODER_UNITS_PER_SPIN = 4096;
  private static final int MOTION_MAGIC_SLOT = 1;
  private final RobotLiftComponents components;

  public RobotLift(final RobotLiftComponents components) {
    this.components = components;
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new MoveLiftByHeightAndKeep(this, () -> -getCurrentHeight()));
  }

  public boolean getMicroSwitchValueReverse() {
    return components.getMicroSwitchReverse().isOpen();
  }

  public void setSpeed(final double speed) {
    components.getMasterMotor().set(speed);
  }

  public void stopLift() {
    components.getMasterMotor().set(0);
  }

  public void moveToHeightByMotionMagic(final double height) {
    components.getMasterMotor().selectProfileSlot(MOTION_MAGIC_SLOT, 0);
    moveToHeight(ControlMode.MotionMagic, height,
        DemandType.ArbitraryFeedForward, -0.1);
  }

  private void moveToHeight(final ControlMode controlMode, double height, final DemandType demandType,
                            final double demandValue) {
    components.getMasterMotor().set(controlMode, cmToEncoderUnit(height), demandType, demandValue);
  }

  public double cmToEncoderUnit(final double cm) {
    return -((cm / ENCODER_UNIT) * ENCODER_UNITS_PER_SPIN);
  }

  public double encoderToCm(final double encoderUnit) {
    return ((encoderUnit / ENCODER_UNITS_PER_SPIN) * ENCODER_UNIT);
  }

  public boolean isOnTarget(final double maxError) {
    return Math.abs((components.getMasterMotor().getClosedLoopTarget(0) -
        components.getMasterMotor().getSelectedSensorPosition(0))) <= maxError;
  }

  public double getCurrentHeight() {
    return encoderToCm(components.getMasterMotor().getSelectedSensorPosition());
  }

  public boolean isOnTarget() {
    return isOnTarget(ENCODER_ERROR);
  }
}

package robot.pitch;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import sensors.Switch.Switch;
import sensors.Switch.TalonSrxReverseMicroswitch;

public class BasicPitchBComponents implements PitchComponents {
  private static final double PERCENTAGE_CLOSED_LOOP_OUTPUT = 1.0;
  private static final double MAX_CLOSED_LOOP_OUTPUT = 1023;
  private static final double MAX_VELOCITY = 800;
  private static final int MOTOR_PORT = 9;
  private static final int CURRENT_LIMIT_AMPS = 40;
  private final WPI_TalonSRX motor;
  private final Switch pitchSwitch;

  public BasicPitchBComponents() {
    motor = new WPI_TalonSRX(5);
    motor.configFactoryDefault();
    motor.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    motor.configPeakCurrentDuration(CURRENT_LIMIT_AMPS, 0);
    motor.configAllSettings(getConfiguration());
    motor.setNeutralMode(NeutralMode.Brake);
    motor.setSelectedSensorPosition(0, 0, 100);
    motor.setInverted(false);

    pitchSwitch =
        new TalonSrxReverseMicroswitch(motor, LimitSwitchSource.Deactivated,
            LimitSwitchNormal.Disabled);
  }

  private TalonSRXConfiguration getConfiguration() {
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    // slot0 = position PIDF
    config.slot0.kP = 0;
    config.slot0.kI = 0;
    config.slot0.kD = 0;
    config.slot0.kF = 0;
    config.slot0.integralZone = 200;
    // slot1 = motion magic FPID
    config.slot1.kP = 3;
    config.slot1.kI = 0.01;
    config.slot1.kD = 30;
    config.slot1.kF = PERCENTAGE_CLOSED_LOOP_OUTPUT * MAX_CLOSED_LOOP_OUTPUT / MAX_VELOCITY;
    config.slot1.integralZone = 300;
    config.motionCruiseVelocity = 800;
    config.motionAcceleration = 800;
    config.clearPositionOnLimitF = false;
    config.continuousCurrentLimit = 40;
    config.peakCurrentLimit = 40;
    return config;
  }

  @Override
  public WPI_TalonSRX getMotor() {
    return motor;
  }

  @Override
  public Switch getSwitch() {
    return pitchSwitch;
  }

  public int getMotorPort() {
    return MOTOR_PORT;
  }
}

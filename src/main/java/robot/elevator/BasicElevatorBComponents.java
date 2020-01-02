package robot.elevator;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import sensors.Switch.Switch;
import sensors.Switch.TalonSrxForwardMicroswitch;
import sensors.Switch.TalonSrxReverseMicroswitch;

public class BasicElevatorBComponents implements ElevatorComponents {
  public static final int ENCODER_ERROR = 100;
  private static final double PERCENTAGE_CLOSED_LOOP_OUTPUT = 1.0;
  private static final double MAX_CLOSED_LOOP_OUTPUT = 1023;
  private static final double MAX_VELOCITY = 1250;
  private static final int MASTER_MOTOR_PORT = 3;
  private static final int SECOND_MOTOR_PORT = 2;
  private static final int CURRENT_LIMIT_AMPS = 35;
  private final WPI_TalonSRX masterMotor;
  private final WPI_VictorSPX secondMotor;
  private final Switch upElevatorSwitch;

  public BasicElevatorBComponents() {
    masterMotor = new WPI_TalonSRX(8);
    masterMotor.configFactoryDefault();
    masterMotor.configPeakCurrentDuration(CURRENT_LIMIT_AMPS, 0);
    masterMotor.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    masterMotor.configAllSettings(getConfiguration());
    masterMotor.setNeutralMode(NeutralMode.Brake);
    masterMotor.enableCurrentLimit(true);
    masterMotor.setSelectedSensorPosition(0, 0, 100);
    masterMotor.setSensorPhase(true);
    masterMotor.setInverted(false);

    secondMotor = new WPI_VictorSPX(4);
    secondMotor.configFactoryDefault();
    secondMotor.setNeutralMode(NeutralMode.Brake);
    secondMotor.setInverted(true);
    secondMotor.follow(masterMotor);

    upElevatorSwitch = new TalonSrxReverseMicroswitch(masterMotor,
        LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    new TalonSrxForwardMicroswitch(masterMotor, LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
  }

  private TalonSRXConfiguration getConfiguration() {
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    // slot0 = position PIDF
    config.slot0.kP = 0;
    config.slot0.kI = 0;
    config.slot0.kD = 0;
    config.slot0.kF = 0;
    config.slot0.integralZone = 300;
    config.slot0.allowableClosedloopError = ENCODER_ERROR;
    // slot1 = motion magic FPID
    config.slot1.kP = 5;
    config.slot1.kI = 0.01;
    config.slot1.kD = 0;
    config.slot1.kF = PERCENTAGE_CLOSED_LOOP_OUTPUT * MAX_CLOSED_LOOP_OUTPUT / MAX_VELOCITY;
    config.slot1.integralZone = 200;
    config.motionCruiseVelocity = 1250;
    config.motionAcceleration = 1600;
    config.forwardSoftLimitEnable = true;
    config.reverseSoftLimitEnable = true;
    config.forwardSoftLimitThreshold = 12700;
    config.reverseSoftLimitThreshold = 0;
    config.peakCurrentLimit = 40;
    config.continuousCurrentLimit = 40;
    return config;
  }

  @Override
  public WPI_TalonSRX getMasterMotor() {
    return masterMotor;
  }

  @Override
  public IMotorController getSecondMotor() {
    return secondMotor;
  }

  @Override
  public Switch getUpElevatorSwitch() {
    return upElevatorSwitch;
  }

  public int getMasterMotorPort() {
    return MASTER_MOTOR_PORT;
  }

  public int getSecondMotorPort() {
    return SECOND_MOTOR_PORT;
  }
}

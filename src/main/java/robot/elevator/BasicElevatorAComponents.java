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

public class BasicElevatorAComponents implements ElevatorComponents {
  public static final int ENCODER_ERROR = 100;
  private static final double PERCENTAGE_CLOSED_LOOP_OUTPUT = 1.0;
  private static final double MAX_CLOSED_LOOP_OUTPUT = 1023;
  private static final double MAX_VELOCITY = 1250;
  private static final int CURRENT_LIMIT_AMPS = 30;
  private static final int MASTER_MOTOR_PORT = 3;
  private static final int SECOND_MOTOR_PORT = 2;
  private final WPI_TalonSRX masterMotor;
  private final WPI_VictorSPX secondMotor;
  private final Switch upElevatorSwitch;

  public BasicElevatorAComponents() {
    masterMotor = new WPI_TalonSRX(4);
    masterMotor.configFactoryDefault();
    masterMotor.configAllSettings(getConfiguration());
    masterMotor.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    masterMotor.configPeakCurrentDuration(0, 0);
    masterMotor.enableCurrentLimit(true);
    masterMotor.setSelectedSensorPosition(0, 0, 0);
    masterMotor.setNeutralMode(NeutralMode.Brake);
    masterMotor.setInverted(true);

    secondMotor = new WPI_VictorSPX(5);
    secondMotor.configFactoryDefault();
    secondMotor.setNeutralMode(NeutralMode.Brake);
    secondMotor.follow(masterMotor);

    upElevatorSwitch = new TalonSrxReverseMicroswitch(masterMotor,
        LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    new TalonSrxForwardMicroswitch(masterMotor, LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    masterMotor.configClearPositionOnLimitR(false, 0);
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
    config.slot1.kP = 2;
    config.slot1.kI = 0.001;
    config.slot1.kD = 10;
    config.slot1.kF = PERCENTAGE_CLOSED_LOOP_OUTPUT * MAX_CLOSED_LOOP_OUTPUT / MAX_VELOCITY;
    config.slot1.integralZone = 200;
    config.motionCruiseVelocity = 1250;
    config.motionAcceleration = 2000;
    config.forwardSoftLimitEnable = false; //find the highest softlimit
    config.reverseSoftLimitEnable = true;
    config.forwardSoftLimitThreshold = 12200;
    config.reverseSoftLimitThreshold = 0;
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

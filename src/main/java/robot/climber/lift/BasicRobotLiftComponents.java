package robot.climber.lift;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import sensors.Switch.Switch;
import sensors.Switch.TalonSrxReverseMicroswitch;

public class BasicRobotLiftComponents implements RobotLiftComponents {
  private static final double PERCENTAGE_CLOSED_LOOP_OUTPUT = 1.0;
  private static final double MAX_CLOSED_LOOP_OUTPUT = 4096;
  private static final double MAX_VELOCITY = 700;
  private static final int CURRENT_LIMIT_AMPS = 30;
  private final WPI_TalonSRX masterMotor;
  private final WPI_VictorSPX slaveMotor;
  private final Switch microSwitchReverse;

  public BasicRobotLiftComponents() {
    masterMotor = new WPI_TalonSRX(21);
    masterMotor.configFactoryDefault();
    masterMotor.configAllSettings(getConfiguration(), 0);
    masterMotor.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    masterMotor.configPeakCurrentDuration(0);
    masterMotor.enableCurrentLimit(true);
    masterMotor.setNeutralMode(NeutralMode.Brake);
    masterMotor.setSelectedSensorPosition(0, 0, 0);
    masterMotor.setSensorPhase(true);
    masterMotor.setInverted(false);

    slaveMotor = new WPI_VictorSPX(8);
    slaveMotor.configFactoryDefault();
    slaveMotor.setNeutralMode(NeutralMode.Brake);
    slaveMotor.setInverted(true);
    slaveMotor.follow(masterMotor);

    microSwitchReverse = new TalonSrxReverseMicroswitch(masterMotor, LimitSwitchSource.FeedbackConnector,
        LimitSwitchNormal.NormallyOpen);
  }


  private TalonSRXConfiguration getConfiguration() {
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    config.slot1.kP = 2;
    config.slot1.kI = 0;
    config.slot1.kD = 0;
    config.slot1.kF = PERCENTAGE_CLOSED_LOOP_OUTPUT * MAX_CLOSED_LOOP_OUTPUT / MAX_VELOCITY;
    config.motionCruiseVelocity = -500;
    config.motionAcceleration = -500;
    config.forwardSoftLimitEnable = false;
    config.reverseSoftLimitEnable = true;
    config.forwardSoftLimitThreshold = 12200;
    config.reverseSoftLimitThreshold = -38500;
    return config;
  }

  public WPI_TalonSRX getMasterMotor() {
    return masterMotor;
  }

  @Override
  public WPI_VictorSPX getSlaveMotor() {
    return slaveMotor;
  }

  public Switch getMicroSwitchReverse() {
    return microSwitchReverse;
  }
}

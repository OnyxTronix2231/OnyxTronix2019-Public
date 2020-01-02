package robot.ballcollector;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class BasicBallCollectorAComponents implements BallCollectorComponents {
  private static final int MASTER_MOTOR_PORT = 4;
  private static final int CURRENT_LIMIT_AMPS = 25;
  private final WPI_TalonSRX masterMotor;

  public BasicBallCollectorAComponents() {
    masterMotor = new WPI_TalonSRX(11);
    masterMotor.configFactoryDefault();
    masterMotor.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    masterMotor.configPeakCurrentDuration(0, 0);
    masterMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled, 0);
    masterMotor.enableCurrentLimit(true);
    masterMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public WPI_TalonSRX getMaster() {
    return masterMotor;
  }

  @Override
  public boolean getMicroSwitchValue() {
    return false;
  }

  public int getMasterPort() {
    return MASTER_MOTOR_PORT;
  }
}

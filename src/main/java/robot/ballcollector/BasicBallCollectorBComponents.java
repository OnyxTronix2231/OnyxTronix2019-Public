package robot.ballcollector;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class BasicBallCollectorBComponents implements BallCollectorComponents {
  private static final int CURRENT_LIMIT_AMPS = 25;
  private static final int CURRENT_PICK_MS = 40;
  private final WPI_TalonSRX masterMotor;
  private final DigitalInput microSwitch;

  public BasicBallCollectorBComponents() {
    masterMotor = new WPI_TalonSRX(6);
    masterMotor.configFactoryDefault();
    masterMotor.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    masterMotor.configPeakCurrentDuration(CURRENT_PICK_MS, 0);
    masterMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
    masterMotor.enableCurrentLimit(true);
    masterMotor.setNeutralMode(NeutralMode.Brake);

    microSwitch = new DigitalInput(8);
  }

  @Override
  public WPI_TalonSRX getMaster() {
    return masterMotor;
  }

  @Override
  public boolean getMicroSwitchValue() {
    return !microSwitch.get();
  }
}

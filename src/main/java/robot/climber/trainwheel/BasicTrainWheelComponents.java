package robot.climber.trainwheel;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import sensors.Switch.Switch;
import sensors.Switch.TalonSrxForwardMicroswitch;

public class BasicTrainWheelComponents implements TrainWheelComponents {
  private final WPI_TalonSRX motor;
  private final Switch microSwitch;

  public BasicTrainWheelComponents() {
    motor = new WPI_TalonSRX(16);
    motor.configFactoryDefault();
    motor.configContinuousCurrentLimit(40);
    motor.configPeakCurrentDuration(0);
    motor.enableCurrentLimit(true);
    motor.setNeutralMode(NeutralMode.Coast);
    microSwitch = new TalonSrxForwardMicroswitch(motor,
        LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
  }

  public SpeedController getMotor() {
    return motor;
  }

  @Override
  public Switch getMicroSwitch() {
    return microSwitch;
  }
}

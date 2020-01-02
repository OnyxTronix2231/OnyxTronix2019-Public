package robot.climber.lift;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import sensors.Switch.Switch;

public interface RobotLiftComponents {
  WPI_TalonSRX getMasterMotor();

  IMotorController getSlaveMotor();

  Switch getMicroSwitchReverse();
}

package robot.elevator;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import sensors.Switch.Switch;

public interface ElevatorComponents {
  WPI_TalonSRX getMasterMotor();

  IMotorController getSecondMotor();

  Switch getUpElevatorSwitch();
}

package robot.pitch;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import sensors.Switch.Switch;

public interface PitchComponents {
  WPI_TalonSRX getMotor();

  Switch getSwitch();
}

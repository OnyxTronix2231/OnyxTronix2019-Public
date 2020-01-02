package robot.climber.trainwheel;

import edu.wpi.first.wpilibj.SpeedController;
import sensors.Switch.Switch;

public interface TrainWheelComponents {
  SpeedController getMotor();

  Switch getMicroSwitch();
}

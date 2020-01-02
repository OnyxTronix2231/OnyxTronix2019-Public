package robot.diskcollector;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import sensors.Switch.Switch;

public interface DiskCollectorComponents {
  DoubleSolenoid getDiskSolenoid();

  Switch getMicroSwitch();

  DoubleSolenoid getLight();
}

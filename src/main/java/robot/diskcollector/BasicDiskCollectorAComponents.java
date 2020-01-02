package robot.diskcollector;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import sensors.Switch.InvertibleSwitch;
import sensors.Switch.Microswitch;
import sensors.Switch.Switch;

public class BasicDiskCollectorAComponents implements DiskCollectorComponents {
  private final DoubleSolenoid diskSolenoid;
  private final Switch microSwitch;
  private final DoubleSolenoid light;

  public BasicDiskCollectorAComponents() {
    diskSolenoid = new DoubleSolenoid(1, 0);
    microSwitch = new InvertibleSwitch(new Microswitch(new DigitalInput(0)), true);
    light = new DoubleSolenoid(3, 2);
  }

  @Override
  public DoubleSolenoid getDiskSolenoid() {
    return diskSolenoid;
  }

  @Override
  public Switch getMicroSwitch() {
    return microSwitch;
  }

  public DoubleSolenoid getLight() {
    return light;
  }
}

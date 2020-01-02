package robot.diskcollector;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import sensors.Switch.InvertibleSwitch;
import sensors.Switch.Microswitch;
import sensors.Switch.Switch;

public class BasicDiskCollectorBComponents implements DiskCollectorComponents {
  private final DoubleSolenoid diskSolenoid;
  private final Switch microSwitch;
  private final DoubleSolenoid light;

  public BasicDiskCollectorBComponents() {
    diskSolenoid = new DoubleSolenoid(1, 0);
    microSwitch = new InvertibleSwitch(new Microswitch(new DigitalInput(0)), true);
    light = new DoubleSolenoid(5, 4);
  }

  @Override
  public DoubleSolenoid getDiskSolenoid() {
    return diskSolenoid;
  }

  @Override
  public Switch getMicroSwitch() {
    return microSwitch;
  }

  @Override
  public DoubleSolenoid getLight() {
    return light;
  }
}

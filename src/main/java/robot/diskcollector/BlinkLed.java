package robot.diskcollector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class BlinkLed extends CommandGroup {
  public BlinkLed(final DiskCollector diskCollector) {
    for (int i = 0; i <= 4; i++) {
      addSequential(new LightOn(diskCollector));
      addSequential(new WaitCommand(0.1));
      addSequential(new LightOff(diskCollector));
      addSequential(new WaitCommand(0.1));
    }
  }
}

package robot.diskcollector;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectDiskBySwitch extends CommandGroup {
  private final DiskCollector diskCollector;

  public CollectDiskBySwitch(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
    addSequential(new WaitTillMicroswitchPressed(diskCollector));
    addSequential(new CollectDisk(diskCollector));
    addSequential(new BlinkLed(diskCollector));
    addSequential(new WaitTillMicroswitchReleased(diskCollector));
  }
}

package robot.diskcollector;

import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class CollectBySwitchOrOpenByJoystick extends ConditionalCommand {
  private final DiskCollector diskCollector;

  public CollectBySwitchOrOpenByJoystick(final DiskCollector diskCollector,
                                         final CollectDiskBySwitch collectDiskBySwitch) {
    super(collectDiskBySwitch);
    this.diskCollector = diskCollector;
  }

  @Override
  protected boolean condition() {
    return !diskCollector.isDiskCollected() && !diskCollector.isDiskIn();
  }
}

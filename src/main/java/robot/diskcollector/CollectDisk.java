package robot.diskcollector;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class CollectDisk extends InstantCommand {
  private final DiskCollector diskCollector;

  public CollectDisk(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
    requires(diskCollector);
  }

  @Override
  protected void initialize() {
    diskCollector.collectDisk();
  }
}

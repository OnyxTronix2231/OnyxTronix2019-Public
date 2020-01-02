package robot.diskcollector;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ReleaseDisk extends InstantCommand {
  private final DiskCollector diskCollector;

  public ReleaseDisk(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
    requires(diskCollector);
  }

  @Override
  protected void initialize() {
    diskCollector.releaseDisk();
  }
}

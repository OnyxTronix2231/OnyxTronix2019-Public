package robot.diskcollector;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class LightOff extends InstantCommand {
  private final DiskCollector diskCollector;

  public LightOff(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
  }

  @Override
  protected void initialize() {
    diskCollector.lightOff();
  }
}

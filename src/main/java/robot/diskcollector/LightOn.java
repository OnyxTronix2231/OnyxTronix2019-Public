package robot.diskcollector;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class LightOn extends InstantCommand {
  private final DiskCollector diskCollector;

  public LightOn(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
  }

  @Override
  protected void initialize() {
    diskCollector.lightOn();
  }
}

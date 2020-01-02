package robot.diskcollector;

import edu.wpi.first.wpilibj.command.Command;

public class WaitTillMicroswitchReleased extends Command {
  DiskCollector diskCollector;

  public WaitTillMicroswitchReleased(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
  }

  @Override
  protected boolean isFinished() {
    return !diskCollector.isDiskIn();
  }
}

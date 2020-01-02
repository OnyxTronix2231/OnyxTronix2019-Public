package robot.diskcollector;

import edu.wpi.first.wpilibj.command.Command;

public class WaitTillMicroswitchPressed extends Command {
  private final DiskCollector diskCollector;

  public WaitTillMicroswitchPressed(final DiskCollector diskCollector) {
    this.diskCollector = diskCollector;
  }

  @Override
  protected boolean isFinished() {
    return diskCollector.isDiskIn();
  }
}

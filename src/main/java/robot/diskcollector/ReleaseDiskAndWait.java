package robot.diskcollector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ReleaseDiskAndWait extends CommandGroup {
  public ReleaseDiskAndWait(final ReleaseDisk releaseDisk) {
    addSequential(releaseDisk);
    addSequential(new WaitCommand(2));
  }
}

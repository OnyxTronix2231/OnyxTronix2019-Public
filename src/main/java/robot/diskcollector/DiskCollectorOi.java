package robot.diskcollector;

import edu.wpi.first.wpilibj.buttons.Trigger;
import onyxTronix.UniqueTriggerCache;

public class DiskCollectorOi {
  public DiskCollectorOi(final UniqueTriggerCache buttonsJoystick, final CollectDisk collectDisk,
                         final ReleaseDiskAndWait releaseDisk) {
    Trigger catchDiskButton = buttonsJoystick.createJoystickTrigger(5);
    catchDiskButton.whenActive(collectDisk);

    Trigger releaseDiskButton = buttonsJoystick.createJoystickTrigger(6);
    releaseDiskButton.whenActive(releaseDisk);
  }
}

package robot.pitch;

import edu.wpi.first.wpilibj.XboxController;
import onyxTronix.Axis;
import onyxTronix.UniqueTriggerCache;

public class PitchOi {
  public PitchOi(Pitch pitch, UniqueTriggerCache buttonsJoystickAxisCache, XboxController buttonsJoystick) {
    buttonsJoystickAxisCache.createJoystickTrigger(Axis.kRightY.value)
        .whileActive(new MovePitchByJoystick(pitch, () -> buttonsJoystick.getRawAxis(Axis.kRightY.value)));
  }
}

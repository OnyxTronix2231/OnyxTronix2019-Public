package robot.elevator;

import edu.wpi.first.wpilibj.XboxController;
import onyxTronix.Axis;
import onyxTronix.UniqueTriggerCache;

public class ElevatorOi {
  public ElevatorOi(Elevator elevator, UniqueTriggerCache buttonsJoystickAxisCache, XboxController buttonsJoystick) {
    buttonsJoystickAxisCache.createJoystickTrigger(Axis.kLeftY.value).whileActive(new MoveElevatorByJoystick(elevator, () -> buttonsJoystick.getRawAxis(Axis.kLeftY.value)));
  }
}

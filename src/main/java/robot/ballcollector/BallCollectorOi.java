package robot.ballcollector;

import edu.wpi.first.wpilibj.GenericHID;
import onyxTronix.UniqueTriggerCache;

public class BallCollectorOi {
  public BallCollectorOi(final BallCollector ballCollector, final UniqueTriggerCache buttonsJoystickAxisCache,
                         final UniqueTriggerCache driveJoystickAxisCache) {
    buttonsJoystickAxisCache.createJoystickTrigger(GenericHID.Hand.kLeft.value).
        whileActive(new CollectBall(ballCollector));
    buttonsJoystickAxisCache.createJoystickTrigger(GenericHID.Hand.kRight.value).
        whileActive(new MoveCollectorBySpeed(ballCollector, -0.8));

    driveJoystickAxisCache.createJoystickTrigger(GenericHID.Hand.kLeft.value).
        whileActive(new CollectBall(ballCollector));
    driveJoystickAxisCache.createJoystickTrigger(GenericHID.Hand.kRight.value).
        whileActive(new MoveCollectorBySpeed(ballCollector, -0.3));
  }
}

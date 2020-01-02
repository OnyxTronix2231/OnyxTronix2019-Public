package robot.levelcontrol;

import onyxTronix.UniqueTriggerCache;
import robot.levelcontrol.levels.*;

public class LevelControlOi {
  public LevelControlOi(final UniqueTriggerCache buttonsCache,
                        final LevelOneFront levelOneFront, final LevelOneRear levelOneRear,
                        final LevelTwoFront levelTwoFront, final LevelThreeFront levelThreeFront,
                        final LevelCargoFront levelCargoFront, final LevelCargoRear levelCargoRear,
                        final SetDiskMode setDiskMode, final SetBallMode setBallMode) {
    buttonsCache.createJoystickTrigger(1).whenActive(levelOneFront);
    buttonsCache.createJoystickTrigger(6).whenActive(levelOneRear);
    buttonsCache.createJoystickTrigger(2).whenActive(levelTwoFront);
    buttonsCache.createJoystickTrigger(4).whenActive(levelThreeFront);
    buttonsCache.createJoystickTrigger(3).whenActive(levelCargoFront);
    buttonsCache.createJoystickTrigger(5).whenActive(levelCargoRear);
    buttonsCache.createJoystickTrigger(7).whenActive(setDiskMode);
    buttonsCache.createJoystickTrigger(8).whenActive(setBallMode);
  }
}

package robot.drivetrain;


import edu.wpi.first.wpilibj.buttons.Trigger;
import onyxTronix.UniqueTriggerCache;
import robot.drivetrain.paths.spath.SPath;

public class DriveTrainOi {
    public DriveTrainOi(final UniqueTriggerCache driveJoystick, final SPath sPath, final DriveStraightByMagic driveStraightByMagic) {
        Trigger driveStraightButton = driveJoystick.createJoystickTrigger(1);
        Trigger driveStraightByMagicButton = driveJoystick.createJoystickTrigger(4);
        driveStraightButton.whenActive(sPath);
        driveStraightByMagicButton.whenActive(driveStraightByMagic);
    }
}

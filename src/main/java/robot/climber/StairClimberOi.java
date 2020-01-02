package robot.climber;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import onyxTronix.UniqueTriggerCache;
import robot.climber.lift.LowerLift;
import robot.climber.lift.RaiseLift;
import robot.climber.pistons.CloseClimberPistons;
import robot.climber.pistons.OpenClimberPistons;
import robot.climber.trainwheel.MoveTrainForward;

public class StairClimberOi {
  public StairClimberOi(final UniqueTriggerCache driveJoystick,
                        final MoveTrainForward moveTrainWheelBySpeed, final RaiseLift raiseLift,
                        final LowerLift lowerLift, final CloseClimberPistons closeClimberPistons, final OpenClimberPistons openClimberPistons) {
    Trigger raiseLiftButton = driveJoystick.createJoystickTrigger(2);
    raiseLiftButton.whileActive(raiseLift);

    Trigger lowerLiftButton = driveJoystick.createJoystickTrigger(7);
    lowerLiftButton.whileActive(lowerLift);

    Trigger moveWheelBySpeedButton = driveJoystick.createJoystickTrigger(8);
    moveWheelBySpeedButton.whileActive(moveTrainWheelBySpeed);

    Trigger closeClimberPistonsButton = driveJoystick.createJoystickTrigger(4);
    closeClimberPistonsButton.whenActive(closeClimberPistons);

    Trigger openClimberPistonsButton = driveJoystick.createJoystickTrigger(3);
    openClimberPistonsButton.whenActive(openClimberPistons);
  }
}

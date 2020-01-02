package robot.drivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import robot.elevator.Elevator;

public class DriveByJoystick extends DriveBySpeed {
  public DriveByJoystick(final XboxController driveJoystick, final DriveTrain driveTrain, final Elevator elevator) {
    super(driveTrain, () -> -driveJoystick.getY(GenericHID.Hand.kLeft),
        () -> driveJoystick.getX(GenericHID.Hand.kRight) * 0.8, elevator);
  }
}

package robot.drivetrain;

import robot.elevator.Elevator;

public class StopDriving extends DriveBySpeed {
  public StopDriving(final DriveTrain driveTrain, final Elevator elevator) {
    super(driveTrain, () -> 0, () -> 0, elevator);
    requires(driveTrain);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}

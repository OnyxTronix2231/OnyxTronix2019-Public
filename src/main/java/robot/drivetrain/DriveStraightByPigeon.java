package robot.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightByPigeon extends Command {

  private final DriveTrain driveTrain;

  public DriveStraightByPigeon(DriveTrain driveTrain) {

    this.driveTrain = driveTrain;
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

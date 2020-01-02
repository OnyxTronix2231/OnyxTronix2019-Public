package robot.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveByDistance extends Command {
  private static final int MIN_CHECKS = 10;
  private final double distanceCm;
  private final DriveTrain driveTrain;
  protected int numberOfSuccessfulChecks;
  protected boolean isFinished;

  public DriveByDistance(final DriveTrain driveTrain, final double distanceCm) {
    this.driveTrain = driveTrain;
    this.distanceCm = distanceCm;
    requires(driveTrain);
  }

  @Override
  protected void initialize() {
    driveTrain.setupEncoders();
    driveTrain.encoderStart(distanceCm);
    isFinished = false;
    numberOfSuccessfulChecks = 0;
  }

  @Override
  protected void execute() {
    if (driveTrain.isLeftEncoderOnTarget() && driveTrain.isRightEncoderOnTarget()) { //Checking if on target
      if (numberOfSuccessfulChecks >= MIN_CHECKS) { //Checking if number of successful checks is high enough
        isFinished = true;
      } else {
        numberOfSuccessfulChecks++;
      }
    } else {
      numberOfSuccessfulChecks = 0;
    }
  }

  @Override
  protected boolean isFinished() {
    return isFinished;
  }

  @Override
  protected void end() {
    driveTrain.disableEncoders();
  }
}

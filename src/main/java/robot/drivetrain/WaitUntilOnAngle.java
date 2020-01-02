package robot.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class WaitUntilOnAngle extends Command {
  private final double endingAngle;
  private final double tolerance;
  private final DriveTrain driveTrain;
  private double currentAngle;

  public WaitUntilOnAngle(final DriveTrain driveTrain, final double endingAngle, final double tolerance) {
    this.driveTrain = driveTrain;
    this.endingAngle = endingAngle;
    this.tolerance = tolerance;
  }

  @Override
  protected void initialize() {
    driveTrain.resetPitchGyro();
  }

  @Override
  protected void execute() {
    currentAngle = driveTrain.getPitchGyroAngle();
  }

  @Override
  protected boolean isFinished() {
    return Math.abs(endingAngle - Math.abs(currentAngle)) < tolerance;
  }
}

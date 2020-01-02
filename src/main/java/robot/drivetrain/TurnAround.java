package robot.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnAround extends Command {
  private final double angle;
  private final DriveTrain driveTrain;

  public TurnAround(final DriveTrain driveTrain, final double angle) {
    this.driveTrain = driveTrain;
    this.angle = angle;
    requires(driveTrain);
  }

  @Override
  protected void initialize() {
    driveTrain.setupPidController(angle);
  }

  @Override
  protected boolean isFinished() {
    return driveTrain.isPidControllerOnTarget();
  }

  @Override
  protected void end() {
    driveTrain.pidControllerReset();
  }
}

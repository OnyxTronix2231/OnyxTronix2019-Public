package robot.pitch;

import edu.wpi.first.wpilibj.command.Command;

public class MovePitchBySpeed extends Command {
  private final double maxSpeed;
  private final Pitch pitch;

  public MovePitchBySpeed(final Pitch pitch, final double maxSpeed) {
    this.pitch = pitch;
    this.maxSpeed = maxSpeed;
    requires(pitch);
  }

  @Override
  protected void execute() {
    pitch.setSpeed(maxSpeed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    pitch.stop();
  }
}

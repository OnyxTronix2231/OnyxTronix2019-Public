package robot.pitch;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class MovePitchByJoystick extends Command {
  private final Pitch pitch;
  private final Supplier<Number> speed;

  public MovePitchByJoystick(final Pitch pitch, final Supplier<Number> speed) {
    this.pitch = pitch;
    this.speed = speed;
    requires(pitch);
  }

  @Override
  protected void execute() {
    pitch.setSpeed(speed.get().doubleValue());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

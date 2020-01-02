package robot.pitch;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class MovePitchToAngleAndKeep extends Command {
  protected final Pitch pitch;
  protected final Supplier<Number> getAngle;
  protected double angle;

  public MovePitchToAngleAndKeep(final Pitch pitch, final Supplier<Number> getAngle) {
    this.pitch = pitch;
    this.getAngle = getAngle;
    requires(pitch);
  }

  @Override
  protected void initialize() {
    angle = getAngle.get().doubleValue();
  }

  @Override
  protected void execute() {
    pitch.moveToAngleByMotionMagic(angle);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }
}

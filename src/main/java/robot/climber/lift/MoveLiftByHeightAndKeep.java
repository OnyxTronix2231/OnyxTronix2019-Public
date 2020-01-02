package robot.climber.lift;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class MoveLiftByHeightAndKeep extends Command {
  protected final RobotLift robotLift;
  protected final Supplier<Number> getHeight;
  protected double targetHeight;

  public MoveLiftByHeightAndKeep(final RobotLift robotLift, final Supplier<Number> getHeight) {
    this.robotLift = robotLift;
    this.getHeight = getHeight;
    requires(robotLift);
  }

  @Override
  protected void initialize() {
    targetHeight = getHeight.get().doubleValue();
  }

  @Override
  protected void execute() {
    robotLift.moveToHeightByMotionMagic(targetHeight);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    robotLift.stopLift();
  }
}

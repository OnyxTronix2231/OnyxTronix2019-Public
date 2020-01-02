package robot.elevator;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class MoveElevatorToHeightAndKeep extends Command {
  protected final Elevator elevator;
  protected final Supplier<Number> getHeight;
  protected double targetHeight;

  public MoveElevatorToHeightAndKeep(final Elevator elevator, final Supplier<Number> getHeight) {
    this.elevator = elevator;
    this.getHeight = getHeight;
    requires(elevator);
  }

  @Override
  protected void initialize() {
    targetHeight = getHeight.get().doubleValue();
  }

  @Override
  protected void execute() {
    elevator.moveToHeightByMotionMagic(targetHeight);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }
}

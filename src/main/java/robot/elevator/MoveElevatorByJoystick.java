package robot.elevator;

import edu.wpi.first.wpilibj.command.Command;

import java.util.function.Supplier;

public class MoveElevatorByJoystick extends Command {
  private final Supplier<Number> speed;
  private final Elevator elevator;

  public MoveElevatorByJoystick(final Elevator elevator, final Supplier<Number> speed) {
    this.elevator = elevator;
    this.speed = speed;
    requires(elevator);
  }

  @Override
  protected void execute() {
    elevator.setSpeed(speed.get().doubleValue());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

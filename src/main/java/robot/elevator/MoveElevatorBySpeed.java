package robot.elevator;

import edu.wpi.first.wpilibj.command.Command;

public class MoveElevatorBySpeed extends Command {
  private final double speed;
  private final Elevator elevator;

  public MoveElevatorBySpeed(final Elevator elevator, final double speed) {
    this.speed = speed;
    this.elevator = elevator;
    requires(elevator);
  }

  @Override
  protected void execute() {
    elevator.setSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    elevator.stop();
  }
}

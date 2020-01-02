package robot.elevator;

public class RaiseElevator extends MoveElevatorBySpeed {
  private static final double MAX_SPEED = 0.4;

  public RaiseElevator(final Elevator elevator) {
    super(elevator, MAX_SPEED);
  }
}
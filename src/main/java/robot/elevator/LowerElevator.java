package robot.elevator;

public class LowerElevator extends MoveElevatorBySpeed {
  private static final double MAX_SPEED = -0.4;

  public LowerElevator(final Elevator elevator) {
    super(elevator, MAX_SPEED);
  }
}

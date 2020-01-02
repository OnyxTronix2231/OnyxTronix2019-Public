package robot.elevator;

public class ElevatorCommands {
  private final LowerElevator lowerElevator;
  private final RaiseElevator raiseElevator;

  public ElevatorCommands(final LowerElevator lowerElevator, final RaiseElevator raiseElevator) {
    this.lowerElevator = lowerElevator;
    this.raiseElevator = raiseElevator;
  }

  public LowerElevator getLowerElevator() {
    return lowerElevator;
  }

  public RaiseElevator getRaiseElevator() {
    return raiseElevator;
  }
}

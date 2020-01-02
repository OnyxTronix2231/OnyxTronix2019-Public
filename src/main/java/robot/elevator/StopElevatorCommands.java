package robot.elevator;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StopElevatorCommands extends InstantCommand {
  public StopElevatorCommands(final Elevator elevator) {
    requires(elevator);
  }
}

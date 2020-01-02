package robot.levelcontrol;

import robot.elevator.Elevator;
import robot.elevator.MoveElevatorToHeightAndKeep;

import java.util.function.Supplier;

public class MoveElevatorToSafeFlippingHeightByHeightAndKeep extends MoveElevatorToHeightAndKeep {
  private final LevelControl levelControl;

  public MoveElevatorToSafeFlippingHeightByHeightAndKeep(final Elevator elevator, final LevelControl levelControl, final Supplier<Number> height) {
    super(elevator, height);
    this.levelControl = levelControl;
  }

  @Override
  protected void initialize() {
    super.initialize();
    targetHeight = levelControl.getElevatorSafeHeight(targetHeight);
  }
}

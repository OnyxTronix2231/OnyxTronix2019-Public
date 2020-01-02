package robot.levelcontrol;

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import robot.elevator.Elevator;
import robot.elevator.MoveElevatorToHeightAndKeep;

import java.util.function.Supplier;

public class ChooseHeightByFlipping extends ConditionalCommand {
  private final LevelControl levelControl;

  public ChooseHeightByFlipping(final Elevator elevator, final LevelControl levelControl, final Supplier<Number> height) {
    super(new MoveElevatorToSafeFlippingHeightByHeightAndKeep(elevator, levelControl, height),
        new MoveElevatorToHeightAndKeep(elevator, height));
    this.levelControl = levelControl;
  }

  @Override
  protected boolean condition() {
    return levelControl.isPitchFlipping();
  }
}

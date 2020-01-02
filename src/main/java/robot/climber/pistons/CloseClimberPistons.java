package robot.climber.pistons;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class CloseClimberPistons extends InstantCommand {
  private ClimberPistons climberPistons;

  public CloseClimberPistons(final ClimberPistons climberPistons) {
    this.climberPistons = climberPistons;
  }

  @Override
  protected void initialize() {
    climberPistons.ClosePistons();
  }

}

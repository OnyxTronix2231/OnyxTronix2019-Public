package robot.climber.pistons;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class OpenClimberPistons extends InstantCommand {

  private final ClimberPistons climberPistons;

  public OpenClimberPistons(final ClimberPistons climberPistons) {
    this.climberPistons = climberPistons;
  }

  @Override
  protected void initialize() {
    climberPistons.OpenPistons();
  }
}

package robot.climber.pistons;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberPistons extends Subsystem {

  final private ClimberPistonsComponents components;

  public ClimberPistons(final ClimberPistonsComponents components) {
    this.components = components;
  }

  @Override
  protected void initDefaultCommand() {
  }

  public void OpenPistons() {
    components.getClimbSolenoid().set(DoubleSolenoid.Value.kForward);
  }

  public void ClosePistons() {
    components.getClimbSolenoid().set(DoubleSolenoid.Value.kReverse);
  }

}

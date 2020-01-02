package robot.climber.pistons;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BasicClimberPistonsComponents implements ClimberPistonsComponents {

  private final DoubleSolenoid climbSolenoid;

  public BasicClimberPistonsComponents() {
    climbSolenoid = new DoubleSolenoid(4, 5);
  }

  @Override
  public DoubleSolenoid getClimbSolenoid() {
    return climbSolenoid;
  }
}

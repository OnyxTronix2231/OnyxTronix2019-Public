package robot.pitch;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PitchCurrentControl extends Command {
  private static final double DEFAULT_MOTOR_RAMP = 0.005;
  private static final int RAMP_TIME_OUT = 100;
  private final BasicPitchAComponents components;
  private final PowerDistributionPanel pdp;

  public PitchCurrentControl(final PowerDistributionPanel pdp, final BasicPitchAComponents components) {
    this.components = components;
    this.pdp = pdp;
    SmartDashboard.putNumber("Pitch Ramp Config", DEFAULT_MOTOR_RAMP);
  }

  @Override
  protected void initialize() {
    double pitchRampConfig = Math.min(SmartDashboard.getNumber("Pitch Ramp Config", DEFAULT_MOTOR_RAMP), 0.01);
    SmartDashboard.putNumber("Pitch Ramp Config", pitchRampConfig);

    components.getMotor().configOpenloopRamp(pitchRampConfig, RAMP_TIME_OUT);
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("Pitch/Motor/Power",
        components.getMotor().getMotorOutputPercent());

    SmartDashboard.putNumber("Pitch/Motor/Current",
        pdp.getCurrent(components.getMotorPort()));

    SmartDashboard.putNumber("Pitch/Motor/Voltage",
        components.getMotor().getMotorOutputVoltage());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

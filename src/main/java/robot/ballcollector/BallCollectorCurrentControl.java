package robot.ballcollector;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallCollectorCurrentControl extends Command {
  private static final double DEFAULT_MOTOR_RAMP = 0.005;
  private static final int RAMP_TIME_OUT = 100;
  private final BasicBallCollectorAComponents components;
  private final PowerDistributionPanel pdp;

  public BallCollectorCurrentControl(final PowerDistributionPanel pdp,
                                     final BasicBallCollectorAComponents components) {
    this.pdp = pdp;
    this.components = components;
    SmartDashboard.putNumber("Ball Ramp Config", DEFAULT_MOTOR_RAMP);

  }

  @Override
  protected void initialize() {
    double ballRampConfig = SmartDashboard.getNumber("Ball Ramp Config", Math.min(DEFAULT_MOTOR_RAMP, 0.01));

    components.getMaster().configOpenloopRamp(ballRampConfig, RAMP_TIME_OUT);
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("BallCollector/FirstMotor/Power",
        components.getMaster().get());

    SmartDashboard.putNumber("BallCollector/FirstMotor/Current",
        pdp.getCurrent(components.getMasterPort()));

    SmartDashboard.getNumber("BallCollector/FirstMotor/Voltage",
        components.getMaster().getMotorOutputVoltage());

    SmartDashboard.getNumber("BallCollector/SecondMotor/Voltage",
        components.getMaster().getMotorOutputVoltage());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

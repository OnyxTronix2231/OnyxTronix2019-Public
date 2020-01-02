package robot.elevator;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorCurrentControl extends Command {
  private static final double DEFAULT_MOTOR_RAMP = 0.005;
  private static final int RAMP_TIME_OUT = 100;
  private final BasicElevatorAComponents components;
  private final PowerDistributionPanel pdp;

  public ElevatorCurrentControl(final PowerDistributionPanel pdp, final BasicElevatorAComponents components) {
    this.components = components;
    this.pdp = pdp;
    SmartDashboard.putNumber("Elevator Ramp Config", DEFAULT_MOTOR_RAMP);
  }

  @Override
  protected void initialize() {
    final double elevatorTrainRampConfig = SmartDashboard.getNumber("Elevator Ramp Config",
        Math.min(DEFAULT_MOTOR_RAMP, 0.01));

    components.getSecondMotor().configOpenloopRamp(elevatorTrainRampConfig, RAMP_TIME_OUT);
    components.getMasterMotor().configOpenloopRamp(elevatorTrainRampConfig, RAMP_TIME_OUT);
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("Elevator/MasterMotor/Voltage",
        components.getMasterMotor().getMotorOutputVoltage());

    SmartDashboard.putNumber("Elevator/MasterMotor/Power",
        components.getMasterMotor().getMotorOutputPercent());

    SmartDashboard.putNumber("Elevator/MasterMotor/Current",
        pdp.getCurrent(components.getMasterMotorPort()));

    SmartDashboard.putNumber("Elevator/SecondMotor/Voltage",
        components.getSecondMotor().getMotorOutputVoltage());

    SmartDashboard.putNumber("Elevator/SecondMotor/Power",
        components.getSecondMotor().getMotorOutputPercent());

    SmartDashboard.putNumber("Elevator/SecondMotor/Current",
        pdp.getCurrent(components.getSecondMotorPort()));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

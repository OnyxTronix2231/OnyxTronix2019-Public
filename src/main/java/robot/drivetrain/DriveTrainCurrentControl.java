package robot.drivetrain;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainCurrentControl extends Command {
  private static final double DEFAULT_MOTOR_RAMP = 0.005;
  private static final int RAMP_TIME_OUT = 100;
  private final BasicDriveTrainAComponents components;
  private final PowerDistributionPanel pdp;

  public DriveTrainCurrentControl(final PowerDistributionPanel pdp, final BasicDriveTrainAComponents components) {
    this.pdp = pdp;
    this.components = components;
    SmartDashboard.putNumber("Drive Train Ramp Config", DEFAULT_MOTOR_RAMP);
  }

  protected void initialize() {
    double driveTrainRampConfig = SmartDashboard.getNumber("Drive Train Ramp Config",
        Math.min(DEFAULT_MOTOR_RAMP, 0.01));

    components.getLeftMaster().configOpenloopRamp(driveTrainRampConfig, RAMP_TIME_OUT);
    components.getFirstLeftSlave().configOpenloopRamp(driveTrainRampConfig, RAMP_TIME_OUT);
    components.getSecondLeftSlave().configOpenloopRamp(driveTrainRampConfig, RAMP_TIME_OUT);
    components.getRightMaster().configOpenloopRamp(driveTrainRampConfig, RAMP_TIME_OUT);
    components.getFirstRightSlave().configOpenloopRamp(driveTrainRampConfig, RAMP_TIME_OUT);
    components.getSecondLeftSlave().configOpenloopRamp(driveTrainRampConfig, RAMP_TIME_OUT);
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("DriveTrain/LeftMaster/Power",
        components.getLeftMaster().getMotorOutputPercent());

    SmartDashboard.putNumber("DriveTrain/LeftMaster/Voltage",
        components.getLeftMaster().getMotorOutputVoltage());

    SmartDashboard.putNumber("DriveTrain/LeftMaster/Current",
        pdp.getCurrent(components.getLeftMasterPort()));

    SmartDashboard.putNumber("DriveTrain/RightMaster/Power",
        components.getRightMaster().getMotorOutputPercent());

    SmartDashboard.putNumber("DriveTrain/RightMaster/Voltage",
        components.getRightMaster().getMotorOutputVoltage());

    SmartDashboard.putNumber("DriveTrain/RightMaster/Current",
        pdp.getCurrent(components.getRightMasterPort()));

    SmartDashboard.putNumber("DriveTrain/LeftFirstSlave/Power",
        components.getFirstLeftSlave().getMotorOutputPercent());

    SmartDashboard.putNumber("DriveTrain/LeftFirstSlave/Voltage",
        components.getFirstLeftSlave().getMotorOutputVoltage());

    SmartDashboard.putNumber("DriveTrain/LeftFirstSlave/Current",
        pdp.getCurrent(components.getFirstLeftSlavePort()));

    SmartDashboard.putNumber("DriveTrain/RightFirstSlave/Power",
        components.getFirstRightSlave().getMotorOutputPercent());

    SmartDashboard.putNumber("DriveTrain/RightFirstSlave/Voltage",
        components.getFirstRightSlave().getMotorOutputVoltage());

    SmartDashboard.putNumber("DriveTrain/RightFirstSlave/Current",
        pdp.getCurrent(components.getFirstRightSlavePort()));

    SmartDashboard.putNumber("DriveTrain/LeftSecondSlave/Power",
        components.getSecondLeftSlave().getMotorOutputPercent());

    SmartDashboard.putNumber("DriveTrain/LeftSecondSlave/Voltage",
        components.getSecondLeftSlave().getMotorOutputVoltage());

    SmartDashboard.putNumber("DriveTrain/LeftSecondSlave/Current",
        pdp.getCurrent(components.getSecondLeftSlavePort()));

    SmartDashboard.putNumber("DriveTrain/RightSecondSlave/Power",
        components.getSecondRightSlave().getMotorOutputPercent());

    SmartDashboard.putNumber("DriveTrain/RightSecondSlave/Voltage",
        components.getSecondRightSlave().getMotorOutputVoltage());

    SmartDashboard.putNumber("DriveTrain/RightSecondSlave/Current",
        pdp.getCurrent(components.getSecondRightSlavePort()));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

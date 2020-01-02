package robot.drivetrain;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import sensors.gyro.NavX;

public class BasicDriveTrainAComponents implements DriveTrainComponents {
  private static final double OPEN_LOOP_RAMP = 0.005;
  private static final double TURN_P = 0.02;
  private static final double TURN_I = 0.0004;
  private static final double TURN_D = 0.1;
  private static final double TURN_F = 0;
  private static final double TURN_PID_TOLERANCE = 3;
  private static final double PERCENTAGE_CLOSED_LOOP_OUTPUT = 1.0;
  private static final double MAX_CLOSED_LOOP_OUTPUT = 1023;
  public static final double  MAX_VELOCITY = 886;
  private static final int LEFT_MASTER_PORT = 15;
  private static final int RIGHT_MASTER_PORT = 0;
  private static final int FIRST_LEFT_SLAVE_PORT = 14;
  private static final int FIRST_RIGHT_SLAVE_PORT = 1;
  private static final int SECOND_LEFT_SLAVE_PORT = 13;
  private static final int SECOND_RIGHT_SLAVE_PORT = 2;
  private static final int CURRENT_LIMIT_AMPS = 35;
  private static final int REMOTE_PIGEON_PORT = 11;

  private final WPI_TalonSRX leftMaster;
  private final WPI_TalonSRX rightMaster;
  private final WPI_TalonSRX leftFirstSlave;
  private final WPI_TalonSRX rightFirstSlave;
  private final WPI_VictorSPX leftSecondSlave;
  private final WPI_VictorSPX rightSecondSlave;
  private final PigeonIMU pigey;
  private final DifferentialDrive differentialDrive;
  private final AHRS ahrs;
  private final NavX pitchGyro;
  private final NavX yawGyro;
  private final PIDController leftYawPidController;
  private final PIDController rightYawPidController;

  public BasicDriveTrainAComponents() {
    leftMaster = new WPI_TalonSRX(2);
    leftMaster.configFactoryDefault();
    leftMaster.configAllSettings(getConfiguration(), 0);
    leftMaster.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    leftMaster.configPeakCurrentDuration(20, 0);
    leftMaster.enableCurrentLimit(true);
    leftMaster.configOpenloopRamp(OPEN_LOOP_RAMP, 0);
    leftMaster.setNeutralMode(NeutralMode.Brake);
    leftMaster.setSelectedSensorPosition(0, 0, 100);

    leftFirstSlave = new WPI_TalonSRX(1);
    leftFirstSlave.configFactoryDefault();
    leftFirstSlave.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyClosed, 0);
    leftFirstSlave.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    leftFirstSlave.configPeakCurrentDuration(20, 0);
    leftFirstSlave.enableCurrentLimit(true);
    leftFirstSlave.configOpenloopRamp(OPEN_LOOP_RAMP, 0);
    leftFirstSlave.setNeutralMode(NeutralMode.Brake);
    leftFirstSlave.follow(leftMaster);

    leftSecondSlave = new WPI_VictorSPX(3);
    leftSecondSlave.configFactoryDefault();
    leftSecondSlave.configOpenloopRamp(OPEN_LOOP_RAMP, 0);
    leftSecondSlave.setNeutralMode(NeutralMode.Brake);
    leftSecondSlave.follow(leftMaster);

    rightMaster = new WPI_TalonSRX(13);
    rightMaster.configFactoryDefault();
    rightMaster.configAllSettings(getConfiguration(), 0);
    rightMaster.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    rightMaster.configPeakCurrentDuration(20, 0);
    rightMaster.enableCurrentLimit(true);
    rightMaster.configOpenloopRamp(OPEN_LOOP_RAMP, 0);
    rightMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setInverted(true);
    rightMaster.setSelectedSensorPosition(0, 0, 100);

    rightFirstSlave = new WPI_TalonSRX(12);
    rightFirstSlave.configFactoryDefault();
    rightFirstSlave.configContinuousCurrentLimit(CURRENT_LIMIT_AMPS, 0);
    rightFirstSlave.configPeakCurrentDuration(20, 0);
    rightFirstSlave.enableCurrentLimit(true);
    rightFirstSlave.configOpenloopRamp(OPEN_LOOP_RAMP, 0);
    rightFirstSlave.setNeutralMode(NeutralMode.Brake);
    rightFirstSlave.setInverted(true);
    rightFirstSlave.follow(rightMaster);

    rightSecondSlave = new WPI_VictorSPX(14);
    rightSecondSlave.configFactoryDefault();
    rightSecondSlave.setNeutralMode(NeutralMode.Brake);
    rightSecondSlave.configOpenloopRamp(OPEN_LOOP_RAMP, 0);
    rightSecondSlave.setInverted(true);
    rightSecondSlave.follow(rightMaster);

    differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
    differentialDrive.setSafetyEnabled(false);
    differentialDrive.setRightSideInverted(false);

    ahrs = new AHRS(SPI.Port.kMXP);
    pitchGyro = new NavX(ahrs, () -> (double) ahrs.getRoll(), () -> (double) ahrs.getRawGyroY());
    yawGyro = new NavX(ahrs, ahrs::getAngle, () -> (double) ahrs.getRawGyroX());

    pigey = new PigeonIMU(new WPI_TalonSRX(11));

    leftYawPidController = new PIDController(TURN_P, TURN_I, TURN_D, TURN_F, yawGyro, leftMaster);
    rightYawPidController = new PIDController(TURN_P, TURN_I, TURN_D, TURN_F, yawGyro, rightMaster);
    leftYawPidController.setAbsoluteTolerance(TURN_PID_TOLERANCE);
    rightYawPidController.setAbsoluteTolerance(TURN_PID_TOLERANCE);

  }

  private TalonSRXConfiguration getConfiguration() {
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    config.slot0.kP = 3;
    config.slot0.kI = 0;
    config.slot0.kD = 0;
    config.slot0.kF = (MAX_CLOSED_LOOP_OUTPUT / MAX_VELOCITY);
    config.slot1.kP = 0.32;//0.32;
    config.slot1.kI = 0;
    config.slot1.kD = 0.55;//21;
    config.slot1.kF = (MAX_CLOSED_LOOP_OUTPUT / MAX_VELOCITY) ;
//    config.slot1.kF = 1;
    config.slot1.integralZone = 0;
    config.slot1.closedLoopPeakOutput = 1.0;
    config.slot2.kF = 0;
    config.slot2.kP = 1.2;
    config.slot2.kI = 0;
    config.slot2.kD = 1;
    config.motionCruiseVelocity = 0; // 900;
    config.motionAcceleration = 0; // 900;
    config.remoteFilter1.remoteSensorDeviceID = REMOTE_PIGEON_PORT;
    config.remoteFilter1.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;
    config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;
    config.auxiliaryPID.selectedFeedbackSensor =  FeedbackDevice.RemoteSensor1;
    return config;
  }

  @Override
  public DifferentialDrive getDifferentialDrive() {
    return differentialDrive;
  }

  @Override
  public NavX getPitchGyro() {
    return pitchGyro;
  }

  @Override
  public NavX getYawGyro() {
    return yawGyro;
  }

  @Override
  public AHRS getNavX() {
    return ahrs;
  }

  @Override
  public PIDController getLeftYawPidController() {
    return leftYawPidController;
  }

  @Override
  public PIDController getRightYawPidController() {
    return rightYawPidController;
  }

  @Override
  public WPI_TalonSRX getLeftMaster() {
    return leftMaster;
  }

  @Override
  public WPI_TalonSRX getRightMaster() {
    return rightMaster;
  }

  @Override
  public IMotorController getFirstLeftSlave() {
    return leftFirstSlave;
  }

  @Override
  public IMotorController getFirstRightSlave() {
    return rightFirstSlave;
  }

  @Override
  public IMotorController getSecondLeftSlave() {
    return leftSecondSlave;
  }

  @Override
  public IMotorController getSecondRightSlave() {
    return rightSecondSlave;
  }

  @Override
  public PigeonIMU getPigey() {
    return pigey;
  }

  public int getLeftMasterPort() {
    return LEFT_MASTER_PORT;
  }

  public int getFirstLeftSlavePort() {
    return FIRST_LEFT_SLAVE_PORT;
  }

  public int getRightMasterPort() {
    return RIGHT_MASTER_PORT;
  }

  public int getFirstRightSlavePort() {
    return FIRST_RIGHT_SLAVE_PORT;
  }

  public int getSecondLeftSlavePort() {
    return SECOND_LEFT_SLAVE_PORT;
  }

  public int getSecondRightSlavePort() {
    return SECOND_RIGHT_SLAVE_PORT;
  }
}

package robot.drivetrain;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import jaci.pathfinder.Trajectory;
import robot.drivetrain.paths.DynamicMotionProfileGenerator;
import robot.elevator.Elevator;
import static robot.drivetrain.BasicDriveTrainAComponents.MAX_VELOCITY;

public class DriveTrain extends Subsystem {
  private static final int ENCODER_UNITS_PER_ROTATION = 1023;
  private static final double WHEEL_DIAMETER = 6;
  private static final double INCH_TO_CM = 2.54;
  public static final double METER_TO_ENCODER_UNITS =
      ENCODER_UNITS_PER_ROTATION / (WHEEL_DIAMETER * INCH_TO_CM * Math.PI) * 100;
  private static final double DRIVE_BY_DISTANCE_TOLERANCE = 100;
  private static final double ARBITRARY_FEED_FORWARD = 0.06;
  private static final double DEAD_BAND = 0.2;
  private static final int PRIMARY_PID = 0;
  private static final int AUX_PID =1;
  private static final int MOTION_MAGIC_PID_SLOT = 0;
  private static final int MOTION_PROFILE_PID_SLOT = 1;
  private static final int MIN_BUFFERED_PT = 10;
  private static final double dt = 0.02;
  public static final DynamicMotionProfileGenerator pathGenerator =
          new DynamicMotionProfileGenerator(WHEEL_DIAMETER,
                  new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, dt, MAX_VELOCITY,
                          1000, 5));
  public static final int DIRECTION = - 1;
  private final DriveTrainComponents components;
  private final XboxController xboxController;
  private final Elevator elevator;


  public DriveTrain(final DriveTrainComponents driveTrainComponents, final XboxController xboxController,
                    final Elevator elevator) {
    components = driveTrainComponents;
    this.xboxController = xboxController;
    this.elevator = elevator;
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new DriveByJoystick(xboxController, this, elevator));
  }

  public double getVelocityLeft() {
    return components.getLeftMaster().getSensorCollection().getQuadratureVelocity();
  }

  public double getVelocityRight() {
    return components.getRightMaster().getSensorCollection().getQuadratureVelocity();
  }

  public double getEncoderPositionLeft() {
    return components.getLeftMaster().getSensorCollection().getQuadraturePosition();
  }

  public double getEncoderPositionRight() {
    return components.getRightMaster().getSensorCollection().getQuadraturePosition();
  }

  public double getLeftOutputCurrent() {
    return components.getLeftMaster().getOutputCurrent();
  }

  public double getRightOutputCurrent() {
    return components.getRightMaster().getOutputCurrent();
  }

  public double getLeftOutputVoltage() {
    return components.getLeftMaster().getMotorOutputVoltage();
  }

  public double getRightOutputVoltage() {
    return components.getRightMaster().getMotorOutputVoltage();
  }

  public short getYAcceleration() {
    short[] acceleration_xyz = new short[3];
    components.getPigey().getBiasedAccelerometer(acceleration_xyz);
    return acceleration_xyz[1];
  }

  public void arcadeDrive(final double forwardSpeed, final double rotationSpeed) {
    components.getDifferentialDrive()
        .arcadeDrive(Math.copySign(forwardSpeed * forwardSpeed, forwardSpeed), rotationSpeed * 0.8, false);
  }

  public boolean isMotionProfileFinished() {
    return components.getLeftMaster().isMotionProfileFinished() && components.getRightMaster().isMotionProfileFinished();
  }

  public void setupPidController(final double angle) {
    setupPidController(angle, components.getLeftYawPidController());
    setupPidController(angle, components.getRightYawPidController());
  }

  public boolean isVisionStart() {
    return Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)) > DEAD_BAND;
  }

  private void setupPidController(final double angle, final PIDController pidController) {
    pidController.reset();
    pidController.setSetpoint(angle + components.getYawGyro().getAngle());
    pidController.enable();
  }

  public double getEncoderUnitsFromCm(final double cm) {
    return (cm * ENCODER_UNITS_PER_ROTATION) / (WHEEL_DIAMETER * INCH_TO_CM * Math.PI);
  }

  public void pidControllerReset() {
    components.getLeftYawPidController().reset();
    components.getRightYawPidController().reset();
  }

  public boolean isPidControllerOnTarget() {
    return components.getLeftYawPidController().onTarget()
        && components.getRightYawPidController().onTarget() && !components.getNavX().isMoving();
  }

  public void encoderStart(final double distanceInCm) {
    double distanceInEncoderUnits = getEncoderUnitsFromCm(distanceInCm);
    components.getLeftMaster().selectProfileSlot(MOTION_MAGIC_PID_SLOT, PRIMARY_PID);
    components.getRightMaster().selectProfileSlot(MOTION_MAGIC_PID_SLOT, PRIMARY_PID);
    components.getLeftMaster().set(ControlMode.MotionMagic, distanceInEncoderUnits,
        DemandType.ArbitraryFeedForward, ARBITRARY_FEED_FORWARD);
    components.getRightMaster().set(ControlMode.MotionMagic, distanceInEncoderUnits,
        DemandType.ArbitraryFeedForward, ARBITRARY_FEED_FORWARD);
  }

  public void setupEncoders() {
    components.getRightMaster().setSelectedSensorPosition(0, PRIMARY_PID, 100);
    components.getLeftMaster().setSelectedSensorPosition(0, PRIMARY_PID, 100);
    components.getFirstLeftSlave().follow(components.getLeftMaster());
    components.getFirstRightSlave().follow(components.getRightMaster());
  }

  public void disableEncoders() {
    components.getLeftMaster().set(ControlMode.PercentOutput, 0);
    components.getRightMaster().set(ControlMode.PercentOutput, 0);
  }

  public boolean isLeftEncoderOnTarget() {
    return Math.abs((components.getLeftMaster().getClosedLoopTarget(PRIMARY_PID)
        - components.getLeftMaster().getSelectedSensorPosition(PRIMARY_PID))) <= DRIVE_BY_DISTANCE_TOLERANCE;
  }

  public boolean isRightEncoderOnTarget() {
    return Math.abs((components.getRightMaster().getClosedLoopTarget(PRIMARY_PID)
        - components.getRightMaster().getSelectedSensorPosition(PRIMARY_PID))) <= DRIVE_BY_DISTANCE_TOLERANCE;
  }

  public void setRightMotorsInverted(final boolean isInverted) {
    components.getRightMaster().setInverted(isInverted);
  }

  public double getPitchGyroAngle() {
    return components.getPitchGyro().getAngle();
  }
  double[] yawPitchRoll = new double[3];
  public double getPigeonYaw() {

    components.getPigey().getYawPitchRoll(yawPitchRoll);
    return yawPitchRoll[0];
  }

  public PigeonIMU getPigey() {
    return components.getPigey();
  }

  public void resetPitchGyro() {
    components.getPitchGyro().reset();
  }

  public void driveByMotionProfile(final BufferedTrajectoryPointStream rightBufferStream,
                                   final BufferedTrajectoryPointStream leftBufferStream) {
    components.getLeftMaster().selectProfileSlot(MOTION_PROFILE_PID_SLOT, PRIMARY_PID);
    components.getRightMaster().selectProfileSlot(MOTION_PROFILE_PID_SLOT, PRIMARY_PID);
    components.getLeftMaster().startMotionProfile(rightBufferStream, MIN_BUFFERED_PT, ControlMode.MotionProfile);
    components.getFirstLeftSlave().follow(components.getLeftMaster());
    components.getRightMaster().startMotionProfile(leftBufferStream, MIN_BUFFERED_PT, ControlMode.MotionProfile);
    components.getFirstRightSlave().follow(components.getRightMaster());
  }
}

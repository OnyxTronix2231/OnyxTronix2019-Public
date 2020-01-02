package robot.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static robot.elevator.ElevatorConstants.OFFSET_FROM_FLOOR;

public class Elevator extends Subsystem {
  private static final double DEAD_BAND = 0.2;
  private static final double ENCODER_UNIT = 3.5 * Math.PI;
  private static final double ARBITRARY_FEED_FORWARD = 0.2;
  private static final double OFFSET = -1.4674; //This is from excel in order to fix the elevator error.
  private static final double OFFSET_MULTIPLIER = 1.087;
  private static final double MIDDLE_HEIGHT = 85;
  private static final double MAX_ELEVATOR = 170 - OFFSET_FROM_FLOOR;
  private static final int ENCODER_UNITS_PER_SPIN = 1023;
  private static final int POSITION_SLOT = 0;
  private static final int MOTION_MAGIC_SLOT = 1;
  private static final Logger logger = LogManager.getLogger(Elevator.class);
  private final ElevatorComponents components;
  private int error = -9999;


  public Elevator(final ElevatorComponents elevatorComponents) {
    components = elevatorComponents;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MoveElevatorToHeightAndKeep(this, this::getCurrentHeight));
  }

  @Override
  public void periodic() {
    if (error != components.getMasterMotor().getClosedLoopError()) {
      error = components.getMasterMotor().getClosedLoopError();
      logger.debug("Elevator error = " + error);
    }
    logger.debug("elevator microSwitch: {}", () -> components.getUpElevatorSwitch().isOpen());
  }

  public double cmToEncoderUnit(final double cm) {
    return ((cm / ENCODER_UNIT) * ENCODER_UNITS_PER_SPIN) / OFFSET_MULTIPLIER - OFFSET;
  }

  public double encoderToCm(final double encoderUnit) {
    return ((encoderUnit / ENCODER_UNITS_PER_SPIN) * ENCODER_UNIT) * OFFSET_MULTIPLIER + OFFSET;
  }

  public void setSpeed(final double speed) {
    components.getMasterMotor().set(speed);
  }

  private void moveToHeight(final ControlMode controlMode, double height, final DemandType demandType,
                            final double demandValue) {
    height -= OFFSET_FROM_FLOOR;
    if (height < 0) {
      height = 0;
    } else if (height > MAX_ELEVATOR) {
      height = MAX_ELEVATOR;
    }
    components.getMasterMotor().set(controlMode, cmToEncoderUnit(height), demandType, demandValue);
  }

  public void moveToHeightByPosition(final double height) {
    components.getMasterMotor().selectProfileSlot(POSITION_SLOT, 0);
    moveToHeight(ControlMode.Position, height,
        DemandType.ArbitraryFeedForward, getArbitraryFeedForwardByCurrentHeight());
  }

  public void moveToHeightByMotionMagic(final double height) {
    components.getMasterMotor().selectProfileSlot(MOTION_MAGIC_SLOT, 0);
    moveToHeight(ControlMode.MotionMagic, height,
        DemandType.ArbitraryFeedForward, getArbitraryFeedForwardByCurrentHeight());
  }


  public void stop() {
    components.getMasterMotor().set(ControlMode.PercentOutput, 0);
  }

  public boolean isOnTarget(final double maxError) {
    return Math.abs((components.getMasterMotor().getClosedLoopTarget(0)
        - components.getMasterMotor().getSelectedSensorPosition(0))) <= maxError;

  }

  public boolean isOnTarget() {
    return isOnTarget(BasicElevatorAComponents.ENCODER_ERROR);
  }

  public boolean isSwitchOnTarget() {
    return components.getUpElevatorSwitch().isOpen();
  }

  public boolean isGoingUp() {
    if (components.getMasterMotor().getInverted()) {
      return components.getMasterMotor().getMotorOutputPercent() < 0;
    }
    return components.getMasterMotor().getMotorOutputPercent() > 0;
  }


  public double getCurrentHeight() {
    return encoderToCm(components.getMasterMotor().getSelectedSensorPosition()) + OFFSET_FROM_FLOOR;
  }

  private double getArbitraryFeedForwardByCurrentHeight() {
    if (getCurrentHeight() > MIDDLE_HEIGHT) {
      return ARBITRARY_FEED_FORWARD;
    }
    return 0;
  }
}

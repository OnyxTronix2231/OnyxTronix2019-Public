package robot.pitch;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static robot.pitch.PitchConstants.MAXIMUM_ANGLE;
import static robot.pitch.PitchConstants.MINIMUM_ANGLE;

public class Pitch extends Subsystem {
  public static final double PITCH_MIDDLE_ANGLE = 90;
  private static final int MAX_ENCODER_UNIT = -7300;
  private static final int MIN_ENCODER_UNIT = 0;
  private static final int ENCODER_OFFSET = MIN_ENCODER_UNIT;
  private static final double ANGLE_TO_ENCODER_UNITS = (MAX_ENCODER_UNIT - ENCODER_OFFSET) / MAXIMUM_ANGLE;
  private static final double ARBITRARY_FEED_FORWARD = -0.1;
  private static final double OFFSET_ANGLE = 0;
  private static final int POSITION_SLOT = 0;
  private static final int MOTION_MAGIC_SLOT = 1;
  private static final Logger logger = LogManager.getLogger(Pitch.class);
  private final PitchComponents components;
  private int error = -9999;

  public Pitch(final PitchComponents pitchComponents) {
    components = pitchComponents;
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new MovePitchToAngleAndKeep(this, this::getCurrentAngle));
  }

  @Override
  public void periodic() {
    if (error != components.getMotor().getClosedLoopError()) {
      error = components.getMotor().getClosedLoopError();
      logger.debug("Pitch error = " + error);
    }
    logger.debug("Pitch Angle: {}", () -> getCurrentAngle());
    SmartDashboard.putNumber("Pitch Angle", getCurrentAngle());
  }


  public void setSpeed(final double speed) {
    components.getMotor().set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    setSpeed(0);
  }

  private void moveToAngle(final ControlMode controlMode, double angle, final DemandType demandType,
                           final double demandValue) {
    angle += OFFSET_ANGLE;
    if (angle < MINIMUM_ANGLE) {
      angle = MINIMUM_ANGLE;
    } else if (angle > MAXIMUM_ANGLE) {
      angle = MAXIMUM_ANGLE;
    }
    angle *= ANGLE_TO_ENCODER_UNITS;
    components.getMotor().set(controlMode, angle, demandType, demandValue);
  }

  public void moveToAngleByPosition(final double angle) {
    components.getMotor().selectProfileSlot(POSITION_SLOT, 0);
    moveToAngle(ControlMode.Position, angle, DemandType.ArbitraryFeedForward,
        ARBITRARY_FEED_FORWARD
            * (getCurrentAngle() < MAXIMUM_ANGLE / 2.0 ? 1 : -1.4)
    );
  }

  public void moveToAngleByMotionMagic(final double angle) {
    components.getMotor().selectProfileSlot(MOTION_MAGIC_SLOT, 0);
    moveToAngle(ControlMode.MotionMagic, angle, DemandType.ArbitraryFeedForward,
        ARBITRARY_FEED_FORWARD
            * (getCurrentAngle() < MAXIMUM_ANGLE / 2.0 ? 1 : -1.4)
    );
  }

  public boolean isPitchOnAngle(final double maxError) {
    return Math.abs((components.getMotor().getClosedLoopTarget(0) -
        components.getMotor().getSelectedSensorPosition(0))) <= maxError;
  }

  public double getCurrentAngle() {
    return components.getMotor().getSelectedSensorPosition(0) / ANGLE_TO_ENCODER_UNITS - OFFSET_ANGLE;
  }
}

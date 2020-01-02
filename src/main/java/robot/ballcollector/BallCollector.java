package robot.ballcollector;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import robot.elevator.Elevator;
import robot.levelcontrol.constants.LevelConstants;

public class BallCollector extends Subsystem {
  public static final double DEAD_BAND = 0.2;
  private static final Logger logger = LogManager.getLogger(BallCollector.class);
  private final BallCollectorComponents components;
  private final Elevator elevator;
  private boolean isBallCollected;

  public BallCollector(final BallCollectorComponents components, final Elevator elevator) {
    this.components = components;
    this.elevator = elevator;
  }

  @Override
  protected void initDefaultCommand() {
  }

  public void collectBallBySpeed(final double speed) {
    double higherSpeed = speed;
    if (elevator.getCurrentHeight() > 100 && higherSpeed < 0) {
      higherSpeed *= 1.2;
    }
    components.getMaster().set(higherSpeed);
  }

  public boolean isBallCollectedByAmp() {
    return isBallCollected;
  }

  public boolean isBallIn() {
    return LevelConstants.BallOrDiskMode.isBallMode();
  }

  public boolean isBallBeingCollected(final XboxController xboxController) {
    return Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kLeft)) <= DEAD_BAND;
  }

  public boolean isEjectionDone(final XboxController xboxController) {
    return Math.abs(xboxController.getTriggerAxis(GenericHID.Hand.kRight)) <= DEAD_BAND;
  }


  @Override
  public void periodic() {
    logger.debug("BallCollector microSwitch", components::getMicroSwitchValue);
    SmartDashboard.putBoolean("Ball Collected", isBallIn());
  }

  public double getAmp() {
    return components.getMaster().getOutputCurrent();
  }

  public void setIsBallCollectedByDeltaAmp(final double amp) {
    if (components.getMaster().getOutputCurrent() - amp > 5 && !isBallCollected) {
      isBallCollected = true;
    }
  }

  public void setBallCollected(final boolean isCollected) {
    isBallCollected = isCollected;
  }
}

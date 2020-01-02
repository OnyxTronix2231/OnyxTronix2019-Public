package robot.diskcollector;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import robot.levelcontrol.constants.LevelConstants;

public class DiskCollector extends Subsystem {
  private static final Logger logger = LogManager.getLogger(DiskCollector.class);
  private final DiskCollectorComponents components;

  public DiskCollector(final DiskCollectorComponents components) {
    this.components = components;
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new CollectDiskBySwitch(this));
  }

  @Override
  public void periodic() {
    logger.debug("Disk in", () -> isDiskIn());
  }

  public void releaseDisk() {
    components.getDiskSolenoid().set(DoubleSolenoid.Value.kReverse);
  }

  public void collectDisk() {
    components.getDiskSolenoid().set(DoubleSolenoid.Value.kForward);
  }

  public boolean isDiskCollected() {
    return components.getDiskSolenoid().get() == DoubleSolenoid.Value.kForward;
  }

  public boolean isDiskIn() {
    return components.getMicroSwitch().isOpen();
  }

  public double chooseHeightByDiskLocation(final double high, final double ground) {
    return high;
  }

  public void lightOn() {
    components.getLight().set(DoubleSolenoid.Value.kForward);
  }

  public void lightOff() {
    components.getLight().set(DoubleSolenoid.Value.kReverse);
  }

  public double chooseByGamePiece(final double diskParameter, final double ballParameter) {
    if (LevelConstants.BallOrDiskMode.isDiskMode() && !LevelConstants.BallOrDiskMode.isBallMode()) {
      return diskParameter;
    } else {
      return ballParameter;
    }
  }
}

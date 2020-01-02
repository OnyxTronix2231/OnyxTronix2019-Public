package robot.climber.trainwheel;

import edu.wpi.first.wpilibj.command.Subsystem;

public class TrainWheel extends Subsystem {
  private final BasicTrainWheelComponents components;

  public TrainWheel(final BasicTrainWheelComponents components) {
    this.components = components;
  }

  public void setSpeed(final double speed) {
    components.getMotor().set(speed);
  }

  public void stop() {
    components.getMotor().set(0);
  }

  @Override
  protected void initDefaultCommand() {
  }

  public boolean getMicroSwitchValue() {
    return components.getMicroSwitch().isOpen();
  }
}

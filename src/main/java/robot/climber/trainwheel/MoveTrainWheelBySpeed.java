package robot.climber.trainwheel;

import edu.wpi.first.wpilibj.command.Command;

public class MoveTrainWheelBySpeed extends Command {
  protected final TrainWheel trainWheel;
  private final double speed;

  public MoveTrainWheelBySpeed(final TrainWheel trainWheel, final double speed) {
    this.trainWheel = trainWheel;
    this.speed = speed;
    requires(trainWheel);
  }

  @Override
  protected void execute() {
    trainWheel.setSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    trainWheel.stop();
  }
}

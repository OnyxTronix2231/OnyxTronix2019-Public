package robot.climber.trainwheel;

public class MoveTrainForward extends MoveTrainWheelBySpeed {
  protected static double SPEED = 0.7;

  public MoveTrainForward(final TrainWheel trainWheel) {
    super(trainWheel, SPEED);
  }

  @Override
  protected boolean isFinished() {
    return trainWheel.getMicroSwitchValue();
  }
}


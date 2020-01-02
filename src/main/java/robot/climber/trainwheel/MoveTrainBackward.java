package robot.climber.trainwheel;

public class MoveTrainBackward extends MoveTrainWheelBySpeed {
  protected static double SPEED = -0.7;

  public MoveTrainBackward(final TrainWheel trainWheel) {
    super(trainWheel, SPEED);
  }
}

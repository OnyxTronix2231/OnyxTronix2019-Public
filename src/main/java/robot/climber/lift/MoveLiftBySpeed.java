package robot.climber.lift;

import edu.wpi.first.wpilibj.command.Command;

public class MoveLiftBySpeed extends Command {
  private final RobotLift robotLift;
  private final double speed;

  public MoveLiftBySpeed(final RobotLift robotLift, final double speed) {
    this.robotLift = robotLift;
    this.speed = speed;
    requires(robotLift);
  }

  @Override
  protected void execute() {
    robotLift.setSpeed(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    robotLift.stopLift();
  }
}

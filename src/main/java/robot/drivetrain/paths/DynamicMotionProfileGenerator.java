package robot.drivetrain.paths;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import robot.TargetPose;

public class DynamicMotionProfileGenerator {
  private final double wheelbaseWidth;
  private final Trajectory.Config config;
  private static final double SECOND_TO_HUNDREDTH_SECOND = 10.0;
  private static final int DT_INDEX = 0;
  private static final int POSITION_INDEX = 1;
  private static final int VELOCITY_INDEX = 2;
  private static final int HEADING_INDEX = 3;
  private static final int CONVERT_MS_TO_SECOND = 1000;

  public DynamicMotionProfileGenerator(double wheelbaseWidth, Trajectory.Config config) {
    this.wheelbaseWidth = wheelbaseWidth;
    this.config = config;
  }

  public Trajectory generateTrajectory(TargetPose target) {
    Waypoint[] points = new Waypoint[]{
        new Waypoint(0, 0, 0),
        new Waypoint(target.getDistance() * Math.cos(target.getAngle()),
            target.getDistance() * Math.sin(target.getAngle()), target.getOrientation())
    };

    return Pathfinder.generate(points, config);
  }

  public Trajectory getLeftTrajectory(Trajectory trajectory) {
    TankModifier modifier = getTankModifier(trajectory);
    return modifier.getLeftTrajectory();
  }

  public Trajectory getRightTrajectory(Trajectory trajectory) {
    TankModifier modifier = getTankModifier(trajectory);
    return modifier.getRightTrajectory();
  }

  private TankModifier getTankModifier(Trajectory trajectory) {
    TankModifier modifier = new TankModifier(trajectory);
    modifier.modify(wheelbaseWidth);
    return modifier;
  }


  public double[][] generateTrajectoryAsArray(Trajectory trajectory) {
    double[][] points = new double[trajectory.length()][4];
    Trajectory.Segment[] segments = trajectory.segments;
    for (int i = 0; i < segments.length; i++) {
      Trajectory.Segment segment = segments[i];
      points[i][0] = segment.dt;
      points[i][1] = segment.position;
      points[i][2] = segment.velocity;
      points[i][3] = segment.heading;
    }
    return points;
  }

  public BufferedTrajectoryPointStream getBufferFromPoints(final double[][] getPoints, final int direction,
                                                           final boolean isLeft,
                                                           final double toEncoderUnits, final int mainPidSlot, final int auxPidPort) {
    final BufferedTrajectoryPointStream bufferedStream = new BufferedTrajectoryPointStream();
    final double[][] points = getPoints;
    int totalCnt = points.length;
    TrajectoryPoint tempPoint = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
    // automatically, you can allow just one
    /* clear the buffer, in case it was used elsewhere */
    bufferedStream.Clear();
    /* Insert every point into buffer, no limit on size */
    for (int i = 0; i < totalCnt; ++i) {
      final double position = points[i][POSITION_INDEX];
      final double velocity = points[i][VELOCITY_INDEX];
      final double heading = points[i][HEADING_INDEX];
      final int durationMilliseconds = (int) points[i][DT_INDEX];
      /* for each point, fill our structure and pass it to API */
      tempPoint.timeDur = durationMilliseconds * CONVERT_MS_TO_SECOND;
      tempPoint.position = direction * toEncoderUnits * position; // Convert Revolutions to
      // Units
      tempPoint.velocity = direction * toEncoderUnits * velocity / SECOND_TO_HUNDREDTH_SECOND; // Convert RPM to
      // Units/100ms
      tempPoint.auxiliaryPos = 0;
      tempPoint.auxiliaryVel = 0;
      tempPoint.profileSlotSelect0 = mainPidSlot; /* which set of gains would you like to use [0,3]? */
      tempPoint.profileSlotSelect1 = auxPidPort; /* auxiliary PID [0,1], leave zero */
      tempPoint.zeroPos = (i == 0); /* set this to true on the first point */
      tempPoint.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
      tempPoint.arbFeedFwd = 0; /* you can add a constant offset to add to PID[0] output here */
      tempPoint.useAuxPID = true;
      bufferedStream.Write(tempPoint);
    }
    return bufferedStream;
  }

  public BufferedTrajectoryPointStream generateLeftBuffer(TargetPose pose, final int direction,
                                                          final double toEncoderUnits, final int mainPidSlot, final int auxPidPort) {
    return getBufferFromPoints(generateTrajectoryAsArray(getLeftTrajectory(generateTrajectory(pose))), direction,
        true,toEncoderUnits, mainPidSlot, auxPidPort);
  }

  public BufferedTrajectoryPointStream generateRightBuffer(TargetPose pose, final int direction,
                                                           final double toEncoderUnits, final int mainPidSlot, final int auxPidSlot) {
    return getBufferFromPoints(generateTrajectoryAsArray(getRightTrajectory(generateTrajectory(pose))), direction,
        false,toEncoderUnits, mainPidSlot, auxPidSlot);
  }
}

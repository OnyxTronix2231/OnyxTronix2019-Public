package robot.drivetrain.paths.spath;

import java.io.File;

import jaci.pathfinder.Trajectory;
import robot.drivetrain.DriveTrain;
import robot.drivetrain.paths.CSVParser;
import robot.drivetrain.paths.DriveByMotionProfile;
import robot.drivetrain.paths.DynamicMotionProfileGenerator;
import static robot.drivetrain.paths.DriveByDynamicPath.MAIN_PID_SLOT;
import static robot.drivetrain.paths.DriveByDynamicPath.AUX_PID_SLOT;

public class SPath extends DriveByMotionProfile {
    private static final int DIRECTION = -1;

    private static final Trajectory leftTrajectory =
            new CSVParser(new File("/home/lvuser/s path.left.pf1.csv")).parseCSV();
    private static final Trajectory rightTrajectory =
            new CSVParser(new File("/home/lvuser/s path.right.pf1.csv")).parseCSV();

    public SPath(final DriveTrain driveTrain,
                 DynamicMotionProfileGenerator motionProfileManager, File logFile) {
        super(driveTrain,
                motionProfileManager.getBufferFromPoints(
                        motionProfileManager.generateTrajectoryAsArray(rightTrajectory),
                        DIRECTION,false, DriveTrain.METER_TO_ENCODER_UNITS, MAIN_PID_SLOT, AUX_PID_SLOT),
                motionProfileManager.getBufferFromPoints(
                        motionProfileManager.generateTrajectoryAsArray(leftTrajectory),
                        DIRECTION,true, DriveTrain.METER_TO_ENCODER_UNITS, MAIN_PID_SLOT, AUX_PID_SLOT), DIRECTION, logFile);
    }
}

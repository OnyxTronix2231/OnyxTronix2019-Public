package robot.drivetrain.paths;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import robot.drivetrain.DriveByDistance;
import robot.drivetrain.DriveTrain;

public class DriveByMotionProfile extends DriveByDistance {
  private final int direction;
  private final BufferedTrajectoryPointStream rightBufferStream;
  private final BufferedTrajectoryPointStream leftBufferStream;
  private final DriveTrain driveTrain;
  private final File logFile;
  private PrintWriter printWriter;

  public DriveByMotionProfile(final DriveTrain driveTrain, final BufferedTrajectoryPointStream rightBufferStream,
                              final BufferedTrajectoryPointStream leftBufferStream, final int direction, File logFile) {
    super(driveTrain, 0);
    this.driveTrain = driveTrain;
    this.rightBufferStream = rightBufferStream;
    this.leftBufferStream = leftBufferStream;
    this.direction = direction;
    this.logFile = logFile;
    try {

      this.printWriter = new PrintWriter(new BufferedWriter(new FileWriter(this.logFile)));
    } catch (IOException e) {
      this.printWriter = null;
      e.printStackTrace();
    }
    printWriter.println("Left Position,Right Position,Left Velocity,Right Velocity,Yaw");
    requires(driveTrain);
  }

  @Override
  protected void initialize() {
    driveTrain.setupEncoders();
    isFinished = false;
    numberOfSuccessfulChecks = 0;
    if (direction == 1) {
      driveTrain.driveByMotionProfile(rightBufferStream, leftBufferStream);
    } else {
      driveTrain.driveByMotionProfile(leftBufferStream, rightBufferStream);
    }
    driveTrain.getPigey().setYaw(360);
  }

  @Override
  protected void execute() {

    super.execute();
    if (printWriter != null) {
      printWriter.println(driveTrain.getEncoderPositionLeft() + "," + driveTrain.getEncoderPositionRight()
        + "," + driveTrain.getVelocityLeft() + "," + driveTrain.getVelocityRight()+","+driveTrain.getPigeonYaw());
    }
    System.out.printf("*\nLeft Position: %f\nRight Position: %f\nLeft Velocity: %f\nRight Velocity: %f\n",
      driveTrain.getEncoderPositionLeft(), driveTrain.getEncoderPositionRight(), driveTrain.getVelocityLeft(),
      driveTrain.getVelocityRight());
  }

  @Override
  protected void end() {
    printWriter.close();
    super.end();
  }

  @Override
  protected boolean isFinished() {
    return driveTrain.isMotionProfileFinished();
  }
}

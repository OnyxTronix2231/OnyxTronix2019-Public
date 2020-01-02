package robot.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.elevator.Elevator;

import java.io.*;
import java.time.LocalTime;
import java.util.function.Supplier;

public class DriveBySpeed extends Command {
  public static final double HIGH_ELEVATOR_ROTATION_SPEED = 0.7;
  public static final double HIGH_ELEVATOR_FORWARD_SPEED = 0.5;
  private final Supplier<Number> forwardSpeedCalculation;
  private final Supplier<Number> rotationSpeedCalculation;
  private final Elevator elevator;
  private final DriveTrain driveTrain;
  private File logFile = null;
  private PrintWriter printWriter = null;

  public DriveBySpeed(final DriveTrain driveTrain, final Supplier<Number> forwardSpeedCalculation,
                      final Supplier<Number> rotationSpeedCalculation, final Elevator elevator) {
    this.driveTrain = driveTrain;
    this.forwardSpeedCalculation = forwardSpeedCalculation;
    this.rotationSpeedCalculation = rotationSpeedCalculation;
    this.elevator = elevator;
    logFile = new File("/home/lvuser/DriveBySpeed.csv");
    try {
      printWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    requires(driveTrain);
  }

  @Override
  protected void initialize() {
    if(printWriter != null) {
      printWriter.println("Time,Left Current,Right Current,Voltage Left,Voltage Right,Velocity Left,Velocity Right, Acceleration");
    }
  }

  @Override
  public void execute() {
    double rotationSpeed = rotationSpeedCalculation.get().doubleValue();
    double forwardSpeed = forwardSpeedCalculation.get().doubleValue();
    if (elevator.getCurrentHeight() > 120) {
      rotationSpeed *= HIGH_ELEVATOR_ROTATION_SPEED;
      forwardSpeed *= HIGH_ELEVATOR_FORWARD_SPEED;
    }
    driveTrain.arcadeDrive(forwardSpeed, rotationSpeed);
    double velocityLeft = driveTrain.getVelocityLeft();
    double velocityRight = driveTrain.getVelocityRight();
    double currentLeft = driveTrain.getLeftOutputCurrent();
    double currentRight = driveTrain.getRightOutputCurrent();

//    System.out.println("*");
//    System.out.println("Max Current: " + (currentLeft > currentRight ? currentLeft : currentRight) + " Amps");
//    System.out.println("Max Velocity: " + (velocityLeft > velocityRight ? velocityLeft : velocityRight));
    printWriter.println(LocalTime.now() + "," + driveTrain.getLeftOutputCurrent() + "," + driveTrain.getRightOutputCurrent() +
            "," + driveTrain.getLeftOutputVoltage() + "," + driveTrain.getRightOutputVoltage() + "," +
            driveTrain.getVelocityLeft() + "," + driveTrain.getVelocityRight() + "," + driveTrain.getYAcceleration());
  }

  @Override
  protected boolean isFinished() {
    return driveTrain.isVisionStart();
  }
}

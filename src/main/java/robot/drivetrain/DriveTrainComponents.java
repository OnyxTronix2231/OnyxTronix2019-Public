package robot.drivetrain;

import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import sensors.gyro.NavX;

public interface DriveTrainComponents {
  IMotorController getFirstLeftSlave();

  IMotorController getFirstRightSlave();

  DifferentialDrive getDifferentialDrive();

  NavX getPitchGyro();

  NavX getYawGyro();

  AHRS getNavX();

  PIDController getLeftYawPidController();

  PIDController getRightYawPidController();

  WPI_TalonSRX getLeftMaster();

  WPI_TalonSRX getRightMaster();

  IMotorController getSecondLeftSlave();

  IMotorController getSecondRightSlave();

  PigeonIMU getPigey();
}

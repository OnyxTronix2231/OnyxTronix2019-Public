package robot;

import java.io.File;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import onyxTronix.JoystickTriggerFactory;
import onyxTronix.JoystickTriggerType;
import onyxTronix.UniqueTriggerCache;
import robot.ballcollector.BallCollector;
import robot.ballcollector.BallCollectorOi;
import robot.ballcollector.BallCollectorComponents;
import robot.ballcollector.BasicBallCollectorAComponents;
import robot.ballcollector.BasicBallCollectorBComponents;
import robot.diskcollector.DiskCollectorComponents;
import robot.diskcollector.BasicDiskCollectorAComponents;
import robot.diskcollector.BasicDiskCollectorBComponents;
import robot.diskcollector.DiskCollector;
import robot.diskcollector.DiskCollectorOi;
import robot.diskcollector.CollectDisk;
import robot.diskcollector.ReleaseDisk;
import robot.diskcollector.ReleaseDiskAndWait;
import robot.drivetrain.BasicDriveTrainAComponents;
import robot.drivetrain.BasicDriveTrainBComponents;
import robot.drivetrain.DriveTrain;
import robot.drivetrain.DriveTrainComponents;
import robot.drivetrain.paths.spath.SPath;
import robot.elevator.*;
import robot.drivetrain.*;
import robot.elevator.BasicElevatorAComponents;
import robot.elevator.BasicElevatorBComponents;
import robot.elevator.Elevator;
import robot.elevator.ElevatorComponents;
import robot.levelcontrol.LevelControl;
import robot.levelcontrol.LevelControlOi;
import robot.levelcontrol.SetBallMode;
import robot.levelcontrol.SetDiskMode;
import robot.levelcontrol.constants.*;
import robot.levelcontrol.levels.*;
import robot.pitch.Pitch;
import  robot.pitch.PitchOi;
import robot.pitch.PitchComponents;
import robot.pitch.BasicPitchAComponents;
import robot.pitch.BasicPitchBComponents;


public class Robot extends TimedRobot {
  private static final int FRONT_EXPOSURE = 39;
  private static final int FPS = 20;
  private static final int BACK_EXPOSURE = 80;
  private static final int CAMERA_WIDTH = 160;
  private static final int CAMERA_HEIGHT = 120;
  private static final RobotName ROBOT_NAME = RobotName.A;
  private static final int FRONT_BRIGHTNESS = 0;
  private static final int BACK_BRIGHTNESS = 95;
  private DriveTrain driveTrain;
  private Pitch pitch;
  private Elevator elevator;

  @Override
  public void robotInit() {
    final XboxController driveJoystick = new XboxController(0);
    final XboxController buttonsJoystick = new XboxController(1);
    final UniqueTriggerCache driveJoystickButtonCache =
        new UniqueTriggerCache(driveJoystick, new JoystickTriggerFactory(JoystickTriggerType.Button));
    final UniqueTriggerCache driveJoystickAxisCache =
        new UniqueTriggerCache(driveJoystick, new JoystickTriggerFactory(JoystickTriggerType.Axis));
    final UniqueTriggerCache buttonsJoystickButtonCache =
        new UniqueTriggerCache(buttonsJoystick, new JoystickTriggerFactory(JoystickTriggerType.Button));
    final UniqueTriggerCache buttonsJoystickAxisCache =
        new UniqueTriggerCache(buttonsJoystick, new JoystickTriggerFactory(JoystickTriggerType.Axis));

    new CameraMaker(CameraServer.getInstance().startAutomaticCapture(), FRONT_EXPOSURE,
        FPS, CAMERA_WIDTH, CAMERA_HEIGHT, FRONT_BRIGHTNESS);
    new CameraMaker(CameraServer.getInstance().startAutomaticCapture(), BACK_EXPOSURE,
        FPS, CAMERA_WIDTH, CAMERA_HEIGHT, BACK_BRIGHTNESS);

    final LevelParameters heightLevelParameters;
    final LevelParameters angleLevelParameters;
    final DiskCollectorComponents diskCollectorComponents;
    final BallCollectorComponents ballCollectorComponents;
    final ElevatorComponents elevatorComponents;
    final DriveTrainComponents driveTrainComponents;
    final PitchComponents pitchComponents;

    if (ROBOT_NAME == RobotName.A) {
      heightLevelParameters = new RobotAHeightParameters();
      angleLevelParameters = new RobotAAngleParameters();
      diskCollectorComponents = new BasicDiskCollectorAComponents();
      ballCollectorComponents = new BasicBallCollectorAComponents();
      elevatorComponents = new BasicElevatorAComponents();
      driveTrainComponents = new BasicDriveTrainAComponents();
      pitchComponents = new BasicPitchAComponents();
    } else {
      heightLevelParameters = new RobotBHeightParameters();
      angleLevelParameters = new RobotBAngleParameters();
      diskCollectorComponents = new BasicDiskCollectorBComponents();
      ballCollectorComponents = new BasicBallCollectorBComponents();
      elevatorComponents = new BasicElevatorBComponents();
      driveTrainComponents = new BasicDriveTrainBComponents();
      pitchComponents = new BasicPitchBComponents();
    }

    elevator = new Elevator(elevatorComponents);
    new ElevatorOi(elevator, buttonsJoystickAxisCache, buttonsJoystick);

    driveTrain = new DriveTrain(driveTrainComponents, driveJoystick, elevator);
    new DriveTrainOi(driveJoystickButtonCache,
            new SPath(driveTrain, driveTrain.pathGenerator,
              new File("/home/lvuser/logs/SPath.csv")), new DriveStraightByMagic(driveTrain));

    pitch = new Pitch(pitchComponents);
    new PitchOi(pitch, buttonsJoystickAxisCache, buttonsJoystick);

    final DiskCollector diskCollector = new DiskCollector(diskCollectorComponents);
    new DiskCollectorOi(driveJoystickButtonCache, new CollectDisk(diskCollector),
        new ReleaseDiskAndWait(new ReleaseDisk(diskCollector)));

    //BallCollector ballCollector = new BallCollector(ballCollectorComponents, elevator);import robot.elevator.*;
    //new BallCollectorOi(ballCollector, buttonsJoystickAxisCache, driveJoystickAxisCache);

    final LevelControl levelControl = new LevelControl(pitch, elevator);
    final LevelOneFront levelOneFront = new LevelOneFront(pitch, elevator, levelControl, diskCollector,
        heightLevelParameters, angleLevelParameters);
    final LevelOneRear levelOneRear = new LevelOneRear(pitch, elevator, levelControl, diskCollector,
        heightLevelParameters, angleLevelParameters);
    final LevelTwoFront levelTwoFront = new LevelTwoFront(pitch, elevator, levelControl, diskCollector,
        heightLevelParameters, angleLevelParameters);
    final LevelThreeFront levelThreeFront = new LevelThreeFront(pitch, elevator, levelControl, diskCollector,
        heightLevelParameters, angleLevelParameters);
    final LevelCargoFront levelCargoFront = new LevelCargoFront(pitch, elevator, levelControl,
        heightLevelParameters, angleLevelParameters);
    final LevelCargoRear levelCargoRear = new LevelCargoRear(pitch, elevator, levelControl,
        heightLevelParameters, angleLevelParameters);
    final SetDiskMode setDiskMode = new SetDiskMode();
    final SetBallMode setBallMode = new SetBallMode();

    new LevelControlOi(buttonsJoystickButtonCache, levelOneFront, levelOneRear, levelTwoFront, levelThreeFront,
        levelCargoFront, levelCargoRear, setDiskMode, setBallMode);

  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    LiveWindow.setEnabled(true);
  }

  @Override
  public void disabledInit() {
    LiveWindow.setEnabled(false);
    elevator.stop();
    pitch.stop();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  private enum RobotName {A, B}
}

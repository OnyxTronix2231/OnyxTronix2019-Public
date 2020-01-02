package robot.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToSpaceshipFromPlatform extends CommandGroup {
  public DriveToSpaceshipFromPlatform(final DriveTrain driveTrain, final double lvlOneLength,
                                      final double angleToSpaceship, final double distanceToSpaceship) {
    addSequential(new DriveByDistance(driveTrain, lvlOneLength));
    addSequential(new TurnAround(driveTrain, angleToSpaceship));
    addSequential(new DriveByDistance(driveTrain, distanceToSpaceship));
  }
}

package robot.pitch;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class StopPitchCommands extends InstantCommand {
  public StopPitchCommands(final Pitch pitch) {
    requires(pitch);
  }
}

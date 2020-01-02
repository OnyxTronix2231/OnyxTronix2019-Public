package robot.ballcollector;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.diskcollector.BlinkLed;
import robot.diskcollector.DiskCollector;

public class BlinkLedByCollecting extends CommandGroup {
  public BlinkLedByCollecting(final BallCollector ballCollector, final DiskCollector diskCollector) {
    addSequential(new WaitUntilBallCollectedByAmp(ballCollector));
    addSequential(new BlinkLed(diskCollector));
  }

}

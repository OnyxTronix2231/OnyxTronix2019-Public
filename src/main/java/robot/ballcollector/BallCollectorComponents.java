package robot.ballcollector;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public interface BallCollectorComponents {
  WPI_TalonSRX getMaster();

  boolean getMicroSwitchValue();
}

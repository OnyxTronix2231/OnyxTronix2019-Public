package robot.drivetrain.paths;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;
import java.io.IOException;

public class CSVParser {
  private File file;

  public CSVParser(final File file) {
    this.file = file;
  }

  public CSVParser(final String pathToFile) {
    file = new File(pathToFile);
  }

  public Trajectory parseCSV() {
    Trajectory trajectory = null;
    try {
      trajectory = Pathfinder.readFromCSV(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return trajectory;
  }
}

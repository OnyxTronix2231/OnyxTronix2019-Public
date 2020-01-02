package robot;

import edu.wpi.cscore.UsbCamera;

public class CameraMaker {
  public CameraMaker(final UsbCamera usbCamera, final int exposure, final int fps, final int cameraWidth,
                     final int cameraHeight, final int brightness) {
    usbCamera.setResolution(cameraWidth, cameraHeight);
    usbCamera.setExposureManual(exposure);
    usbCamera.setFPS(fps);
    usbCamera.setBrightness(brightness);
  }
}

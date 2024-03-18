package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FailedController {
  @FXML private Button buttonClose;

  /** Closes the application. */
  @FXML
  private void onClose() {
    Platform.exit();
  }
}

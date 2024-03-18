package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class SucceededController {
  @FXML private Button buttonClose;

  /**
   * Closes the application.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClose(ActionEvent event) throws ApiProxyException, IOException {
    Platform.exit();
  }
}

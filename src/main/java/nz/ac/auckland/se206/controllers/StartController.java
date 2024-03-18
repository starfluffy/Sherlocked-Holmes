package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class StartController extends HelperMethods {
  @FXML private Button buttonStart;
  @FXML private TextField textFieldLoading;

  /**
   * Initializes the start view.`
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onStart(ActionEvent event) throws ApiProxyException, IOException {
    textFieldLoading.setVisible(true);
    SceneManager.addAppUi(
        AppUi.WATSON, new FXMLLoader(App.class.getResource("/fxml/watson.fxml")).load());
    App.setUi(AppUi.WATSON);
  }
}

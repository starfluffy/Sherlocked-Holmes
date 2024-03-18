package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class CodeController extends HelperMethods {
  @FXML private Button buttonGoBack;
  @FXML private TextField fieldAnswer;
  @FXML private Button buttonBack;
  @FXML private Button buttonSubmit;
  @FXML private Button button1;
  @FXML private Button button2;
  @FXML private Button button3;
  @FXML private Button button4;
  @FXML private Button button5;
  @FXML private Button button6;
  @FXML private Button button7;
  @FXML private Button button8;
  @FXML private Button button9;
  @FXML private Button button0;

  private ArrayList<String> answerInputs = new ArrayList<String>();

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onClickBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.ROOM);
  }

  /**
   * Clears the last input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClickBackSpace(ActionEvent event) throws ApiProxyException, IOException {
    if (!answerInputs.isEmpty()) {
      answerInputs.remove(answerInputs.size() - 1);
      fieldAnswer.clear();
      for (String input : answerInputs) {
        fieldAnswer.appendText(input);
      }
    }
  }

  /**
   * Adds the number to the answer input.
   *
   * @param num the number to add
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  private void onClickNumber(String num) {
    if (answerInputs.size() > 8) {
      showDialog(
          "Info",
          "Limit reached",
          "It is only an 8 digit code that is required my dear apprentice.");
    }
    answerInputs.add(num);
    fieldAnswer.clear();
    for (String input : answerInputs) {
      fieldAnswer.appendText(input);
    }
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick1(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("1");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick2(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("2");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick3(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("3");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick4(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("4");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick5(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("5");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick6(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("6");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick7(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("7");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick8(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("8");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick9(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("9");
  }

  /**
   * Adds the number to the answer input.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClick0(ActionEvent event) throws ApiProxyException, IOException {
    onClickNumber("0");
  }

  /**
   * Checks if the answer is correct.
   *
   * @param event
   * @throws ApiProxyException
   * @throws IOException
   */
  @FXML
  private void onClickSubmit(ActionEvent event) throws ApiProxyException, IOException {
    String answer = "04051891";
    if (fieldAnswer.getText().equals(answer)) {
      showSucceededScene();
    } else {
      showDialog(
          "Info",
          "Faulty deduction",
          "Alas, dear seeker, your deduction has missed the mark. Keep your mind sharp and try"
              + " again!");
    }
    System.out.println("Submit button clicked");
  }

  /**
   * Navigates back to a succeded screen.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  private void showSucceededScene() {
    App.setUi(AppUi.SUCCEEDED);
  }
}

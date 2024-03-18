package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

/** Controller class for the chat view. */
public class ChatController extends HelperMethods {
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button buttonSend;
  @FXML private Label labelLoading;
  @FXML private Label labelTimer;
  @FXML private Button buttonGoBack;

  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    createThread(new ChatMessage("user", GptPromptEngineering.getRiddleWithGivenWord("violin")));

    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
  }

  /**
   * Creates a thread to run the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   */
  private void createThread(ChatMessage msg) {
    Task<Void> runAi =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            runGpt(msg);
            return null;
          }
        };

    Thread runAiThread = new Thread(runAi, "Run AI");
    runAiThread.start();
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      isLoading();
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();
      chatCompletionRequest.addMessage(result.getChatMessage());
      appendChatMessage(result.getChatMessage());
      isNotLoading();
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Enables the input text field and send and go back buttons.
   *
   * @returns void
   */
  private void enableInput() {
    inputText.setDisable(false);
    buttonSend.setDisable(false);
    buttonGoBack.setDisable(false);
  }

  /**
   * Disables the input text field and send and go back buttons.
   *
   * @returns void
   */
  private void disableInput() {
    inputText.setDisable(true);
    buttonSend.setDisable(true);
    buttonGoBack.setDisable(true);
  }

  /**
   * Shows the loading label and disables the input text field and send and go back buttons.
   *
   * @returns void
   */
  private void isLoading() {
    labelLoading.setVisible(true);
    disableInput();
  }

  /**
   * Hides the loading label and enables the input text field and send and go back buttons.
   *
   * @returns void
   */
  private void isNotLoading() {
    labelLoading.setVisible(false);
    enableInput();
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    String message = inputText.getText();
    if (message.trim().isEmpty()) {
      return;
    }
    Task<Void> runAi =
        new Task<Void>() {
          /**
           * Sends the player's input to the GPT model and waits for a response. While waiting for a
           * response, it will call another method that shows a loading label. Once it is finished
           * getting a response, it will call another method to hide the loading label. If the
           * response starts with "Correct", then the player has solved the riddle. The input text
           * field and send button are disabled and a dialog is shown congratulating the player. The
           * player is then navigated to the diary view. The game's state also changes so that the
           * riddle is solved. Otherwise, the player's input is appended to the chat text area and
           * the input text field is cleared.
           *
           * @returns Void
           */
          @Override
          protected Void call() throws Exception {
            isLoading();
            inputText.clear();
            ChatMessage msg = new ChatMessage("user", message);
            appendChatMessage(msg);
            isNotLoading();
            ChatMessage lastMsg = runGpt(msg);
            if (lastMsg.getRole().equals("assistant")
                && lastMsg.getContent().startsWith("Correct")) {
              inputText.setDisable(true);
              buttonSend.setDisable(true);
              GameState.isRiddleResolved = true;
              Platform.runLater(
                  () -> {
                    showDialog(
                        "Info",
                        "Correct answer",
                        "Congratulations, you've uncovered the elusive answer to the perplexing"
                            + " riddle!");

                    App.setUi(AppUi.DIARY);
                  });
            }
            return null;
          }
        };
    Thread runAiThread = new Thread(runAi, "Run AI");
    runAiThread.start();
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.ROOM);
  }
}

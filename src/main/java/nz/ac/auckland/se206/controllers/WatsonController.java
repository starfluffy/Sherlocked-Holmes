package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import nz.ac.auckland.se206.speech.TextToSpeech;

public class WatsonController extends HelperMethods {
  @FXML private TextArea chatTextArea;
  @FXML private Label labelLoading;
  @FXML private Label labelWatson;
  @FXML private Button buttonContinue;

  private ChatCompletionRequest chatCompletionRequest;
  private TextToSpeech textToSpeech;
  private Thread runSpeechThread;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  public void initialize() throws ApiProxyException {
    textToSpeech = new TextToSpeech();
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    createThread(new ChatMessage("user", GptPromptEngineering.getWelcomeMessage()));
  }

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
    labelWatson.setText("Watson:\n" + msg.getContent() + "\n\n");
    createSpeechThread(msg);

    buttonContinue.setDisable(false);
  }

  private void createSpeechThread(ChatMessage msg) {
    Task<Void> runSpeech =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            textToSpeech.speak(msg.getContent());
            return null;
          }
        };

    runSpeechThread = new Thread(runSpeech, "Run speech");
    runSpeechThread.start();
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
      Platform.runLater(
          () -> {
            appendChatMessage(result.getChatMessage());
          });
      isNotLoading();
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  private void isLoading() {
    labelLoading.setVisible(true);
  }

  private void isNotLoading() {
    labelLoading.setVisible(false);
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

  @FXML
  private void onClickContinue(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.ROOM);
    GameState.isGameStarted = true;
    textToSpeech.terminate();
    runSpeechThread.interrupt();
    countDown();
  }
}

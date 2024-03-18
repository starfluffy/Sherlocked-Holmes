package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for the room view. */
public class RoomController extends HelperMethods {

  @FXML private Rectangle rectangleDoor;
  @FXML private Rectangle rectangleViolin;
  @FXML private Rectangle rectangleDiary;
  @FXML private Label labelTimer;
  @FXML private Rectangle rectangleBackground;
  @FXML private Label labelViolinText;
  @FXML private ImageView imageViewHint;
  @FXML private Label labelHint;
  @FXML private Button buttonGoBack;
  @FXML private Label labelTimerBlack;

  private HashMap<String, String> hints = new HashMap<>();

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    labelTimerBlack.textProperty().bind(HelperMethods.timeDisplay);
    appendHints();
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");
    GameState.hasDoorBeenClicked = true;
    App.setUi(AppUi.CODE);
  }

  /**
   * Handles the click event on the diary.
   *
   * @param event the mouse event
   */
  @FXML
  public void onClickDiary(MouseEvent event) {
    System.out.println("diary clicked");
    GameState.hasDiaryBeenClicked = true;
    if (!GameState.isRiddleResolved) {
      showDialog(
          "Info",
          "Solve the riddle!",
          "My dear friend, it seems that the key to unlocking the diary lies in the resolution of a"
              + " riddle, which will reveal the means to access its concealed treasures.");
      App.setUi(AppUi.CHAT);
    } else {
      App.setUi(AppUi.DIARY);
    }
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void onClickViolin(MouseEvent event) {
    System.out.println("violin clicked");
    GameState.hasViolinBeenClicked = true;
    showDialog(
        "Info",
        "Violin found",
        "Curiously, there is a violin present; I find myself wondering about the reason behind its"
            + " presence in this particular setting.");
  }

  /**
   * Handles the click event on the go back button.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the room view
   */
  @FXML
  private void onClickBack(ActionEvent event) throws ApiProxyException, IOException {
    imageViewHint.setVisible(false);
    labelHint.setVisible(false);
    buttonGoBack.setVisible(false);
    labelTimerBlack.setVisible(false);
    labelTimer.setVisible(true);
  }

  /*Shows the hint in the Watson chat bubble */
  private void showHint(String typeOfHint) {
    imageViewHint.setVisible(true);
    labelHint.setVisible(true);
    buttonGoBack.setVisible(true);
    labelTimerBlack.setVisible(true);
    labelTimer.setVisible(false);
    labelHint.setText(hints.get(typeOfHint));
  }

  /**
   * Handles the click event on the hint button. Calls a separate method that shows the hint in the
   * Watson chat bubble. The hint that it shows is dependent on the game state. If the diary hasn't
   * been clicked yet or the violin and diary have been clicked and the riddle is not yet resolved,
   * then it will show a hint about the diary. If the diary has been clicked and the violin hasn't
   * been clicked yet and the riddle is not yet resolved, then it will show a hint about the violin.
   * If the diary and violin have been clicked and the riddle is resolved but the door has not been
   * clicked, then it will show a hint about the door. If the diary has ben clicked and the riddle
   * is resolved and the door has been clicked, then it will show a hint about the diary and door.
   * Otherwise, it will show a hint about the diary. For all of these cases, it will call a separate
   * method that shows the hint in the Watson chat
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the room view
   * @returns void
   */
  @FXML
  private void onClickHint(ActionEvent event) throws ApiProxyException, IOException {
    if (!GameState.hasDiaryBeenClicked
        || (GameState.hasViolinBeenClicked
            && GameState.hasDiaryBeenClicked
            && !GameState.isRiddleResolved)) {
      showHint("diary");
    } else if (!GameState.hasViolinBeenClicked
        && GameState.hasDiaryBeenClicked
        && !GameState.isRiddleResolved) {
      showHint("violin");
    } else if (!GameState.hasDoorBeenClicked
        && GameState.hasDiaryBeenClicked
        && GameState.hasViolinBeenClicked
        && GameState.isRiddleResolved) {
      showHint("door");

    } else if (GameState.isRiddleResolved && GameState.hasDoorBeenClicked) {
      showHint("diary and door");
    } else {
      showHint("diary");
    }
  }

  /**
   * Appends a chat message to a hashmap of hints. adds the hints one by one. THe key of the hashmap
   * is the type of hint, and the value is the hint itself. The hints are generated by a GPT model
   * and based on the state that the player is in of the game. The haspmap will be accessed when
   * showing the hint to the user and will be accessed by using the type of hint that will be shown.
   * e.g. to get the hint about a diary, use the word (string) "diary" as the key to access the
   * hint. There are hints for the diary, violin, door, diary and door, and no hint. There are no
   * input parameters for this method.
   *
   * @returns void
   */
  private void appendHints() {
    hints.put(
        "diary",
        "Intriguingly, the diary within this room beckons to be examined, for it holds the cryptic"
            + " knowledge that might unravel the puzzling enigma at hand.");
    hints.put(
        "violin",
        "Within this chamber, the violin beckons to be played, igniting a path to mysteries"
            + " untold.");
    hints.put(
        "door",
        "As my eyes swept across the room, they landed on the door, a gateway to potential"
            + " revelations lurking beyond its sturdy frame.");
    hints.put(
        "no",
        "Rest assured, my dear apprentice, the answers to your escape reside within the vast"
            + " repository of knowledge you already possess.");
    hints.put(
        "diary and door",
        "While studying the room, my attention gravitated towards the diary, holding potential"
            + " clues that could unlock the mystery concealed behind the very door we seek to"
            + " breach.");
  }
}

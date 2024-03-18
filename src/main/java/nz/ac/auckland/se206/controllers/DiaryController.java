package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.HelperMethods;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class DiaryController extends HelperMethods {
  @FXML private Button buttonGoBack;
  @FXML private Label labelTimer;
  @FXML private Label labelLeftPage;
  @FXML private Label labelRightPage;
  @FXML private TextField textFieldLeft;
  @FXML private TextField textFieldRight;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    labelTimer.textProperty().bind(HelperMethods.timeDisplay);
    appendText();
  }

  /**
   * Adds half of a diary entry to the left label first, then the second half to the right label.
   * The diary entry is written from Sherlock Holme's point of view of the day he and Professor
   * Moriarty fell into reichenbach fall and was generated from chatGPT. The full diary entry is as
   * follows: 04/05/1891
   *
   * <p>Dear Diary,
   *
   * <p>Words cannot adequately capture the magnitude of the events that transpired today. Moriarty
   * and I stood on the precipice of Reichenbach Falls, our eyes locked in a battle of wit and will.
   * Time seemed to halt as we exchanged banter, each testing the limits of the other's intellect.
   *
   * <p>Suddenly, an intense struggle erupted between us, consuming every fiber of our beings. In a
   * moment of raw intensity, we fell, entwined in a deadly embrace, hurtling down into the chasm
   * below. The torrential fury of the waterfall enveloped us, crashing against our bodies with a
   * vengeance.
   *
   * <p>As the swirling waters threatened to consume us, I dug deep into my reserve of strength and
   * cunning. With one last desperate act, I managed to break free from Moriarty's grasp, and the
   * rush of water tore us apart. My heart pounded, knowing that I had successfully escaped death's
   * clutches, but realizing the evil genius of Moriarty now lay lost beneath the relentless
   * cascade.
   *
   * <p>I write this entry, bruised and battered, grateful to have survived this epic encounter. The
   * game may be over, but the echoes of our clash will forever reverberate through the corridors of
   * my mind.
   *
   * <p>Yours faithfully
   *
   * @returns void
   */
  private void appendText() {
    labelLeftPage.setText(
        "04/05/1891\r\n"
            + "\r\n"
            + "Dear Diary,\r\n"
            + "\r\n"
            + "Words cannot adequately capture the magnitude of the events that transpired today."
            + " Moriarty and I stood on the precipice of Reichenbach Falls, our eyes locked in a"
            + " battle of wit and will. Time seemed to halt as we exchanged banter, each testing"
            + " the limits of the other's intellect.\r\n"
            + "\r\n"
            + "Suddenly, an intense struggle erupted between us, consuming every fiber of our"
            + " beings. In a moment of raw intensity, we fell, entwined in a deadly embrace,"
            + " hurtling down into the chasm below. The torrential fury of the waterfall enveloped"
            + " us, crashing against our bodies with a vengeance.\r\n");
    labelRightPage.setText(
        "As the swirling waters threatened to consume us, I dug deep into my reserve of strength"
            + " and cunning. With one last desperate act, I managed to break free from Moriarty's"
            + " grasp, and the rush of water tore us apart. My heart pounded, knowing that I had"
            + " successfully escaped death's clutches, but realizing the evil genius of Moriarty"
            + " now lay lost beneath the relentless cascade.\r\n"
            + "\r\n"
            + "I write this entry, bruised and battered, grateful to have survived this epic"
            + " encounter. The game may be over, but the echoes of our clash will forever"
            + " reverberate through the corridors of my mind.\r\n"
            + "\r\n"
            + "Yours faithfully");
  }

  /**
   * Handles the click event on the go back button.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the room view
   */
  @FXML
  private void onClickBack(ActionEvent event) throws ApiProxyException, IOException {
    App.setUi(AppUi.ROOM);
  }
}

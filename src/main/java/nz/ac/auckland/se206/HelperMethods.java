package nz.ac.auckland.se206;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import nz.ac.auckland.se206.SceneManager.AppUi;

public abstract class HelperMethods {
  protected static StringProperty timeDisplay = new SimpleStringProperty("120");
  protected static StringProperty watsonDisplay = new SimpleStringProperty("");

  /**
   * Count down from 120 seconds (2 minutes). Only start when the user enters the room. Every second
   * that it counts down from, it will update a timer label by calling another method. If the user
   * does not escape the room in time, the failed scene will be shown by calling another method. If
   * the user has not solved the riddle yet and there is only 1 minute left, a dialog box will be
   * shown to remind the user to solve the riddle.
   *
   * @returns void
   */
  protected void countDown() {
    int countdownSeconds = 120;

    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(
        new TimerTask() {
          int secondsRemaining = countdownSeconds;

          @Override
          public void run() {
            if (secondsRemaining > 0) {
              if (secondsRemaining == (countdownSeconds / 2) && !GameState.isRiddleResolved) {
                Platform.runLater(
                    () -> {
                      showDialog(
                          "Info",
                          String.valueOf(countdownSeconds / 2) + " seconds remaining",
                          "Only one minute left, hurry and escape the room, my dear apprentice!");
                    });
              }
              int seconds = secondsRemaining;
              Platform.runLater(
                  () -> {
                    updateTimerText(seconds);
                  });
              secondsRemaining--;

            } else {
              showFailedScene();
              timer.cancel();
            }
          }
        },
        0,
        1000);
  }

  protected void showFailedScene() {
    App.setUi(AppUi.FAILED);
  }

  protected void updateTimerText(int time) {
    String timeToDisplay;
    int minutes = time / 60;
    int seconds = time % 60;
    if (seconds < 10) {
      timeToDisplay = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
    } else {
      timeToDisplay = String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }
    timeDisplay.set(timeToDisplay);
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  protected void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

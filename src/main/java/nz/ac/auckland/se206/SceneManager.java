package nz.ac.auckland.se206;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

  public enum AppUi {
    ROOM,
    CHAT,
    START,
    FAILED,
    SUCCEEDED,
    WATSON,
    CODE,
    DIARY
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  public static void addAppUi(AppUi ui, Parent root) {
    sceneMap.put(ui, root);
  }

  public static Parent getUi(AppUi ui) {
    return sceneMap.get(ui);
  }
}

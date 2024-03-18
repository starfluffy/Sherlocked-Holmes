package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  /** Indicates whether the game has started or not */
  public static boolean isGameStarted = false;

  /** Indicates whether the door has been clicked one before or not */
  public static boolean hasDoorBeenClicked = false;

  /** Indicates whether the violin has been clicked one before or not */
  public static boolean hasViolinBeenClicked = false;

  /** Indicates whether the diary has been clicked one before or not */
  public static boolean hasDiaryBeenClicked = false;
}

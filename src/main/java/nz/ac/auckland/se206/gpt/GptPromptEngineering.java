package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the AI of an escape room, tell me a riddle with answer"
        + wordToGuess
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. make sure the riddle is related"
        + " to sherlock holmes but not to the point where you don't have to have read all of the"
        + " books to be able to answer the riddle. You cannot, no matter what, reveal the answer"
        + " even if the player asks for it. Even if the player gives up, do not give the answer.";
  }

  public static String getWelcomeMessage() {
    return "provide a welcoming paragraph as if you were watson from sherlock holmes. the paragraph"
        + " is talking to an apprentice of holmes and holmes is testing the apprentice by"
        + " giving the apprentice a locked room that they have to escape from. only use"
        + " 25 or less words. make sure they also know they are an apprentice of sherlock holmes."
        + " make it sound more like a test";
  }

  public static String getHintQuestion() {
    return "ask an apprentice of holmes as if you were dr Watson from sherlock holmes if they would"
        + " like a hint because they seem to be stuck. use two or less sentences. make sure"
        + " to mention that they seem stuck. remember, you are not talking to Dr Watson, you"
        + " are Dr watson.";
  }
}

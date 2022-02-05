package jp.gr.java_conf.spica.wordle.game.domain.application;

import com.google.common.base.Preconditions;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.Answer;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;


public class WordleGame {

  private final int limit;
  private final AllWord allWord;
  private final Answer answer;

  public WordleGame(int limit,
      AllWord allWord) {
    this.limit = limit;
    this.allWord = allWord;
    this.answer = new Answer(allWord.random());
  }

  public WordleGame(int limit, AllWord allWord,
      Answer answer) {
    this.limit = limit;
    this.allWord = allWord;
    this.answer = answer;
    Preconditions.checkArgument(allWord.contains(answer.word()));
  }

  public GameResult start(IWordleStrategy strategy) {
    strategy.init(allWord);
    for (int i = 0; i < limit; i++) {
      Word candidate = strategy.answer(limit - i);
      if (!allWord.contains(candidate)) {
        throw new RuntimeException(candidate + " is not contained in the dictionary");
      }
      WordMatchingResult result = answer.matches(candidate);
      if (result.allMatch()) {
        return new GameResult(answer, GameResultType.WIN);
      }
      strategy.feedback(candidate, answer.matches(candidate));
    }
    return new GameResult(answer, GameResultType.LOOSE);
  }
}

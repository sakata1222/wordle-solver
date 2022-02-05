package jp.gr.java_conf.spica.wordle.solver.simple;

import static org.assertj.core.api.Assertions.assertThat;

import jp.gr.java_conf.spica.wordle.game.domain.application.GameResult;
import jp.gr.java_conf.spica.wordle.game.domain.application.GameResultType;
import jp.gr.java_conf.spica.wordle.game.domain.application.WordleGame;
import jp.gr.java_conf.spica.wordle.game.domain.file.WordFileRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.Answer;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SimpleStrategyTest {

  private static final IWordRepository wordRepository = new WordFileRepository();
  private static final int tryCount = 6;
  private static AllWord allWord;

  @BeforeAll
  static void beforeAll() {
    allWord = wordRepository.allWords();
  }

  @ParameterizedTest(name = "The answer is {0}")
  @CsvSource({
      "could",
      "lucky",
      "drive",
      "light",
      "proxy",
      "query",
      "tiger",
      "whack",
      // "sugar"
      // "petti"
  })
  void scenario(String answer) {
    WordleGame game = new WordleGame(tryCount, allWord, new Answer(new Word(answer)));
    GameResult result = game.start(new SimpleStrategy());
    assertThat(result.type()).isEqualTo(GameResultType.WIN);
  }
}
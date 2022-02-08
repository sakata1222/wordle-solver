package jp.gr.java_conf.spica.wordle.solver.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.LetterMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.MatchType;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ContainedLettersTest {

  @Nested
  class ContainsWOL {

    private ContainedLetters owxly = new ContainedLetters()
        .apply(new WordMatchingResult(List.of(
            new LetterMatchingResult(new Letter('o'), MatchType.CONTAINS),
            new LetterMatchingResult(new Letter('w'), MatchType.CONTAINS),
            new LetterMatchingResult(new Letter('x'), MatchType.NONE),
            new LetterMatchingResult(new Letter('l'), MatchType.MATCH),
            new LetterMatchingResult(new Letter('y'), MatchType.NONE))));

    @ParameterizedTest(name = "{0} should be meets condition : {1}")
    @CsvSource({
        "wolyy, true",
        "yywol, true",
        "lowyy, true",
        "owyyy, false",
        "yywly, false",
        "aorld, false",
        "wxrle, false"
    })
    void meetsCondition(String input, boolean expected) {
      AtomicInteger idCounter = new AtomicInteger();
      assertThat(owxly.meetsCondition(new Word(idCounter.getAndIncrement(), input))).isEqualTo(
          expected);
    }
  }
}
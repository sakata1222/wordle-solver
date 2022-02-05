package jp.gr.java_conf.spica.wordle.solver.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.LetterMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.MatchType;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FixedLettersTest {

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("The condition is wXrXd")
  class Wxrxd {

    private FixedLetters wxrxd = new FixedLetters(5)
        .apply(new WordMatchingResult(List.of(
            new LetterMatchingResult(new Letter("w"), MatchType.MATCH),
            new LetterMatchingResult(new Letter("x"), MatchType.NONE),
            new LetterMatchingResult(new Letter("r"), MatchType.MATCH),
            new LetterMatchingResult(new Letter("x"), MatchType.CONTAINS),
            new LetterMatchingResult(new Letter("d"), MatchType.MATCH))));


    @ParameterizedTest(name = "{0} should be meets condition : {1}")
    @CsvSource({
        "world, true",
        "warcd, true",
        "woxld, false",
        "aorld, false",
        "worle, false"
    })
    void meetsCondition(String input, boolean expected) {
      assertThat(wxrxd.meetsCondition(new Word(input))).isEqualTo(expected);
    }

    @Test
    @DisplayName("The previous 1st letter is w, but a is specified")
    void apply_fails_inconsistent_result() {
      assertThatExceptionOfType(IllegalArgumentException.class)
          .isThrownBy(() -> wxrxd.apply(new WordMatchingResult(List.of(
              new LetterMatchingResult(new Letter("a"), MatchType.MATCH),
              new LetterMatchingResult(new Letter("x"), MatchType.NONE),
              new LetterMatchingResult(new Letter("r"), MatchType.MATCH),
              new LetterMatchingResult(new Letter("x"), MatchType.CONTAINS),
              new LetterMatchingResult(new Letter("d"), MatchType.MATCH)))));
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("The condition is updated with 4th l")
    class Wxrld {

      private FixedLetters wxrld = wxrxd.apply(new WordMatchingResult(List.of(
          new LetterMatchingResult(new Letter("x"), MatchType.NONE),
          new LetterMatchingResult(new Letter("x"), MatchType.NONE),
          new LetterMatchingResult(new Letter("x"), MatchType.NONE),
          new LetterMatchingResult(new Letter("l"), MatchType.MATCH),
          new LetterMatchingResult(new Letter("x"), MatchType.NONE))));

      @ParameterizedTest(name = "{0} should be meets condition : {1}")
      @CsvSource({
          "world, true",
          "warcd, false",
          "woxld, false",
          "aorld, false",
          "worle, false"
      })
      void meetsCondition(String input, boolean expected) {
        assertThat(wxrld.meetsCondition(new Word(input))).isEqualTo(expected);
      }
    }
  }
}
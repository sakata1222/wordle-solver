package jp.gr.java_conf.spica.wordle.game.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AnswerTest {

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("The answer is world")
  class World {

    private Answer answer = new Answer("world");

    @ParameterizedTest(name = "when input is {0}, then allMatch should be {1}")
    @CsvSource({
        "world, true",
        "wordl, false",
        "worle, false"
    })
    void matchAllMatch(String in, boolean match) {
      Word word = new Word(in);
      assertThat(answer.matches(word).allMatch()).isEqualTo(match);
    }

    @Test
    void matches() {
      Word word = new Word("worde");
      WordMatchingResult result = answer.matches(word);
      assertThat(result).isEqualTo(
          new WordMatchingResult(List.of(
              new LetterMatchingResult(new Letter("w"), MatchType.MATCH),
              new LetterMatchingResult(new Letter("o"), MatchType.MATCH),
              new LetterMatchingResult(new Letter("r"), MatchType.MATCH),
              new LetterMatchingResult(new Letter("d"), MatchType.CONTAINS),
              new LetterMatchingResult(new Letter("e"), MatchType.NONE)
          ))
      );
    }
  }
}
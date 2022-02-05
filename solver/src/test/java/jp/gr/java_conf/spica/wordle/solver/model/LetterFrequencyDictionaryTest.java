package jp.gr.java_conf.spica.wordle.solver.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LetterFrequencyDictionaryTest {

  @TestInstance(Lifecycle.PER_CLASS)
  @Nested
  @DisplayName("The dictionary contains 2 A and 1 B")
  class A2B1 {

    private final LetterFrequencyDictionary dictionary = new LetterFrequencyDictionary(
        Map.of(
            new Letter("a"), new LetterCount(2L),
            new Letter("b"), new LetterCount(1L)));

    @ParameterizedTest(name = "The count of {0} should be {1}")
    @CsvSource({
        "aaaa, 2",
        "abcd, 3",
        "bbcd, 1",
        "dddd, 0"
    })
    void countFrequency(String word, Long expected) {
      assertThat(dictionary.countFrequency(new Word(word)))
          .isEqualTo(new LetterCount(expected));
    }

    @Test
    void findMostFrequentWord() {
      assertThat(dictionary.findMostFrequentWord(new WordSet(4, Set.of(
          new Word("aaaa"),
          new Word("abcd"),
          new Word("bbcd"),
          new Word("dddd")))))
          .isEqualTo(new Word("abcd"));
    }
  }
}
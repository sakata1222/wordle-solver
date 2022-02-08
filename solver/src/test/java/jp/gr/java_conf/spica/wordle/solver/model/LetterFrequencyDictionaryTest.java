package jp.gr.java_conf.spica.wordle.solver.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;
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
            new Letter('a'), new LetterCount(2L),
            new Letter('b'), new LetterCount(1L)));

    @ParameterizedTest(name = "The count of {0} should be {1}")
    @CsvSource({
        "aaaa, 2",
        "abcd, 3",
        "bbcd, 1",
        "dddd, 0"
    })
    void countFrequency(String word, Long expected) {
      AtomicInteger idCounter = new AtomicInteger();
      assertThat(dictionary.countFrequency(new Word(idCounter.getAndIncrement(), word)))
          .isEqualTo(new LetterCount(expected));
    }

    @Test
    void findMostFrequentWord() {
      assertThat(dictionary.findMostFrequentWord(new WordList(4, List.of(
          new Word(0, "aaaa"),
          new Word(1, "abcd"),
          new Word(2, "bbcd"),
          new Word(3, "dddd")))))
          .isEqualTo(new Word(1, "abcd"));
    }
  }
}
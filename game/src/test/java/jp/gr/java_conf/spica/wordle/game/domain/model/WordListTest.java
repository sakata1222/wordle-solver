package jp.gr.java_conf.spica.wordle.game.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

class WordListTest {

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @DisplayName("mount, mound, found, sound, round")
  class Small {

    private final WordList wordList = new WordList(5, List.of(
        new Word(0, "mount"),
        new Word(1, "mound"),
        new Word(2, "found"),
        new Word(3, "sound"),
        new Word(4, "round")));

    @Test
    void split() {
      Map<MatchTypes, WordList> split = wordList.split(new Word(5, "first"));
      assertThat(split).hasSize(5);
    }
  }
}
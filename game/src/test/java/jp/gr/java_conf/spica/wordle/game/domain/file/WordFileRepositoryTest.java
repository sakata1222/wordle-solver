package jp.gr.java_conf.spica.wordle.game.domain.file;

import static org.assertj.core.api.Assertions.assertThat;

import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordFileRepositoryTest {

  @ParameterizedTest(name = "Words should contain {1}")
  @CsvSource(value = {
      "0, anlas",
      "12971, gimpy"
  })
  void allWords(int id, String text) {
    AllWord word = new WordFileRepository().allWords();
    assertThat(word.contains(new Word(id, text))).isTrue();
  }
}
package jp.gr.java_conf.spica.wordle.game.domain.file;

import static org.assertj.core.api.Assertions.assertThat;

import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WordFileRepositoryTest {


  @ParameterizedTest(name = "Dictionay should contain {0}")
  @CsvSource(value = {
      "anlas",
      "gimpy"
  })
  void allWords(String text) {
    AllWord word = new WordFileRepository().allWords();
    assertThat(word.contains(new Word(text))).isTrue();
  }
}
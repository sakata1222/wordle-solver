package jp.gr.java_conf.spica.wordle.solver.score;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import jp.gr.java_conf.spica.wordle.game.domain.file.WordFileRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AccurateQueryScoreCalculatorTest {

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  class Small {

    private final HybridQuerySelector selector = new HybridQuerySelector(
        new ApproximateCalculator(), 10);

    private final WordList queries = new WordList(5, List.of(
        new Word(0, "mount"),
        new Word(1, "mound"),
        new Word(2, "found"),
        new Word(3, "sound"),
        new Word(4, "round"),
        new Word(5, "first")));

    private final WordList wordList = new WordList(5, List.of(
        new Word(0, "mount"),
        new Word(1, "mound"),
        new Word(2, "found"),
        new Word(3, "sound"),
        new Word(4, "round")));

    @Test
    void findMinimumWord() {
      assertThat(selector.select(queries, wordList))
          .isEqualTo(new Word(5, "first"));
    }

    @ParameterizedTest(name = "When the query is {0}, score should be {1}")
    @CsvSource({
        "mount, 2.6",
        "first, 2"
    })
    void evaluationScore(String query, double expected) {
      Word wQuery = new Word(0, query);
      assertThat(
          selector.evaluationQueryScore(queries.getSameWord(wQuery), queries, wordList))
          .isEqualTo(expected, Offset.offset(0.01d));
    }
  }

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  class Failing {

    private final IWordRepository wordRepository = new WordFileRepository();
    private AllWord allWord;
    private final HybridQuerySelector selector = new HybridQuerySelector(
        new ApproximateCalculator(), 10);

    @BeforeAll
    void beforeAll() {
      allWord = wordRepository.allWords();
    }

    @Test
    void failing() {
      WordList remain = new WordList(5, List.of(
          new Word(180, "BELLE"),
          new Word(191, "BIBLE"),
          new Word(254, "BOULE"),
          new Word(296, "BUGLE"),
          new Word(687, "EXILE"),
          new Word(935, "GUILE"),
          new Word(1306, "NOBLE"),
          new Word(2254, "WHILE"),
          new Word(2261, "WHOLE")));
      Word query = selector.select(allWord.allWords(), remain);
      System.out.println(query);
      System.err.println(remain.split(query));
      assertThat(remain.split(query))
          .hasSizeGreaterThan(1);
    }
  }
}
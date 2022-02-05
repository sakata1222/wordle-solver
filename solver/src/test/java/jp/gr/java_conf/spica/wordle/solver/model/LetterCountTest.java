package jp.gr.java_conf.spica.wordle.solver.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class LetterCountTest {

  @Test
  void comparator() {
    assertThat(
        Stream.of(new LetterCount(1), new LetterCount(0), new LetterCount(3))
            .sorted()
            .collect(Collectors.toList())).containsExactly(
        new LetterCount(0), new LetterCount(1), new LetterCount(3));
  }
}
package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.Objects;

public record Answer(Word word) {

  public Answer {
    Objects.requireNonNull(word);
  }

  public Answer(String string) {
    this(new Word(Integer.MAX_VALUE, string));
  }

  public WordMatchingResult matches(Word word) {
    return this.word.matches(word);
  }
}

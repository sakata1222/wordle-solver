package jp.gr.java_conf.spica.wordle.game.domain.model;

import com.google.common.base.Preconditions;

public record Letter(char letter) {

  public Letter(char letter) {
    Preconditions.checkArgument(Character.isAlphabetic(letter));
    this.letter = Character.toUpperCase(letter);
  }

  boolean matches(Letter other) {
    return letter == other.letter;
  }

  @Override
  public String toString() {
    return String.valueOf(letter);
  }
}

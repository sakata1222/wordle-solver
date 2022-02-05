package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.Locale;

public record Letter(String letter) {

  public Letter(String letter) {
    if (letter.length() != 1) {
      throw new IllegalArgumentException("Length of " + letter + " must be 1");
    }
    this.letter = letter.toLowerCase(Locale.ENGLISH);
  }

  public Letter(char c) {
    this(String.valueOf(c));
  }

  boolean equalsIgnoreCase(Letter other) {
    return letter.equalsIgnoreCase(other.letter);
  }

  boolean matches(Letter other) {
    return this.letter.equalsIgnoreCase(other.letter);
  }

  @Override
  public String toString() {
    return letter;
  }
}

package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.Arrays;

public enum MatchType {
  MATCH("M"),

  CONTAINS("C"),

  NONE("-");

  private final String character;

  MatchType(String character) {
    this.character = character;
  }

  public String character() {
    return character;
  }

  public static MatchType valueOf(char v) {
    return Arrays.stream(values())
        .filter(e -> e.character().charAt(0) == v)
        .findFirst()
        .orElseThrow();
  }

  public static boolean isValid(char v) {
    return Arrays.stream(values())
        .anyMatch(e -> e.character().charAt(0) == v);
  }
}

package jp.gr.java_conf.spica.wordle.game.domain.model;

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
}

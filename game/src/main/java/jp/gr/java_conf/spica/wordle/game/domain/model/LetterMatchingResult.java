package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.Objects;

public record LetterMatchingResult(
    Letter letter,
    MatchType matchType
) {

  public LetterMatchingResult {
    Objects.requireNonNull(letter);
    Objects.requireNonNull(matchType);
  }

}

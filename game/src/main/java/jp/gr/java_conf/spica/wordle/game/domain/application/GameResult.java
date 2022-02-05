package jp.gr.java_conf.spica.wordle.game.domain.application;

import java.util.Objects;
import jp.gr.java_conf.spica.wordle.game.domain.model.Answer;

public record GameResult(Answer answer, GameResultType type) {

  public GameResult {
    Objects.requireNonNull(answer);
    Objects.requireNonNull(type);
  }
}

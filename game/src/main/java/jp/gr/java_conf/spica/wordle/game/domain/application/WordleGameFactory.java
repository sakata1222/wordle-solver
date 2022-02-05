package jp.gr.java_conf.spica.wordle.game.domain.application;

import javax.inject.Inject;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordRepository;

public record WordleGameFactory(
    IWordRepository wordRepository) {

  @Inject
  public WordleGameFactory {
  }

  public WordleGame newGame(int remain) {
    return new WordleGame(remain, wordRepository.allWords());
  }
}

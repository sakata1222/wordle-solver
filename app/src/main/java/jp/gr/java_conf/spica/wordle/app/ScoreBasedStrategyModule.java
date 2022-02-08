package jp.gr.java_conf.spica.wordle.app;

import com.google.inject.AbstractModule;
import jp.gr.java_conf.spica.wordle.game.domain.file.WordFileRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.solver.score.ScoreBasedStrategy;

public class ScoreBasedStrategyModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(IWordRepository.class).to(WordFileRepository.class);
    bind(IWordleStrategy.class).to(ScoreBasedStrategy.class);
  }
}

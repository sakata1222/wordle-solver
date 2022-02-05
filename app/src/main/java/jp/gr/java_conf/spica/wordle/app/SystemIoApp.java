package jp.gr.java_conf.spica.wordle.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import jp.gr.java_conf.spica.wordle.game.domain.application.GameResult;
import jp.gr.java_conf.spica.wordle.game.domain.application.WordleGameFactory;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;

public class SystemIoApp {

  public static void main(String args[]) {
    Injector injector = Guice.createInjector(new SimpleStrategyModule());
    WordleGameFactory gameFactory = injector.getInstance(WordleGameFactory.class);
    GameResult result = gameFactory.newGame(6).start(injector.getInstance(IWordleStrategy.class));
    System.out.println(result);
  }
}

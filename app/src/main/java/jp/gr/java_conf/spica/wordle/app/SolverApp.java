package jp.gr.java_conf.spica.wordle.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.solver.application.AnswerSolver;
import jp.gr.java_conf.spica.wordle.solver.application.AnswerSolverFactory;

public class SolverApp {

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new ScoreBasedSolverModule());
    AnswerSolverFactory factory = injector.getInstance(AnswerSolverFactory.class);
    IWordleStrategy strategy = injector.getInstance(IWordleStrategy.class);
    AnswerSolver solver = factory.newSolver(6, strategy);
    solver.solve(strategy);
  }
}

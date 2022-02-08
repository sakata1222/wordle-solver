package jp.gr.java_conf.spica.wordle.solver.application;

import javax.inject.Inject;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.solver.model.IEvaluationResultSupplier;

public class AnswerSolverFactory {

  private final IEvaluationResultSupplier resultSupplier;
  private final IWordRepository wordRepository;

  @Inject
  public AnswerSolverFactory(
      IEvaluationResultSupplier resultSupplier,
      IWordRepository wordRepository) {
    this.resultSupplier = resultSupplier;
    this.wordRepository = wordRepository;
  }

  public AnswerSolver newSolver(int limit, IWordleStrategy strategy) {
    return new AnswerSolver(resultSupplier, wordRepository.allWords(), strategy, limit);
  }
}

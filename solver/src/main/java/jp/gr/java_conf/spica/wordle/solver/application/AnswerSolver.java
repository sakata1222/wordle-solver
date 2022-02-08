package jp.gr.java_conf.spica.wordle.solver.application;

import java.io.PrintStream;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;
import jp.gr.java_conf.spica.wordle.solver.model.IEvaluationResultSupplier;

public class AnswerSolver {

  private final IEvaluationResultSupplier resultSupplier;
  private final AllWord allWord;
  private final IWordleStrategy wordleStrategy;
  private final PrintStream ps;
  private final int limit;

  public AnswerSolver(
      IEvaluationResultSupplier resultSupplier,
      AllWord allWord,
      IWordleStrategy wordleStrategy,
      int limit) {
    this.resultSupplier = resultSupplier;
    this.allWord = allWord;
    this.wordleStrategy = wordleStrategy;
    this.ps = System.out;
    this.limit = limit;
  }

  public void solve(IWordleStrategy wordleStrategy) {
    int remain = limit;
    wordleStrategy.init(allWord);
    while (remain > 0) {
      Word query = wordleStrategy.answer(remain);
      ps.println(query.toSimpleString());
      WordMatchingResult result = resultSupplier.matches(query);
      wordleStrategy.feedback(query, result);
      if (result.allMatch()) {
        ps.println("WIN");
        return;
      }
    }
  }
}

package jp.gr.java_conf.spica.wordle.solver.score;

import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public class ApproximateCalculator {

  public double evaluationQueryScore(Word query, WordList remain) {
    double score = 1d;
    for (WordList sub : remain.splitResult(query)) {
      score += (double) sub.size() / remain.size() * approximate(sub);
    }
    return score;
  }

  private double approximate(WordList remain) {
    return 0.43 * Math.log(remain.size()) + 1;
  }
}

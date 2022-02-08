package jp.gr.java_conf.spica.wordle.solver.score;

import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public class AccurateQuerySelector {

  public double evaluationQueryScore(Word query, WordList queryCandidate, WordList remain) {
    if (remain.size() == 1) {
      return 1;
    }
    double score = 1;
    for (WordList sub : remain.splitResult(query)) {
      score += (double) sub.size() / remain.size() *
          evaluationRemainScore(queryCandidate.remove(query), sub);
    }
    return score;
  }

  private double evaluationRemainScore(WordList queryCandidate, WordList remain) {
    if (remain.size() == 1) {
      return 1;
    }
    double minScore = Double.MAX_VALUE;
    for (Word query : queryCandidate.words()) {
      double score = evaluationQueryScore(query, queryCandidate.remove(query), remain);
      minScore = Math.min(minScore, score);
    }
    return minScore;
  }
}

package jp.gr.java_conf.spica.wordle.solver.score;

import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public class AccurateQueryScoreCalculator {

  public double evaluationQueryScore(Word query, WordList queryCandidate, WordList remain) {
    return evaluationQueryScore(query, queryCandidate, remain, 1);
  }

  private double evaluationQueryScore(
      Word query,
      WordList queryCandidate,
      WordList remain,
      int depth) {
    if (remain.size() == 1) {
      return 1;
    }
    if (depth > 6) {
      return 100000d;
    }
    double score = 1;
    for (WordList sub : remain.splitResult(query)) {
      if (sub.size() == remain.size()) {
        return 100000d; // the query does not split the word set, the query is not effective.
      }
      score += (double) sub.size() / remain.size() *
          evaluationRemainScore(queryCandidate, sub, depth);
    }
    return score;
  }

  private double evaluationRemainScore(WordList queryCandidate, WordList remain, int depth) {
    if (remain.size() == 1) {
      return 1;
    }
    double minScore = Double.MAX_VALUE;
    for (Word query : queryCandidate.words()) {
      double score = evaluationQueryScore(query, queryCandidate, remain, depth + 1);
      minScore = Math.min(minScore, score);
      if (minScore == 1.0d) {
        continue; // 1 is the minimum
      }
    }
    return minScore;
  }
}

package jp.gr.java_conf.spica.wordle.solver.score;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public class HybridQuerySelector {

  private final ApproximateCalculator approximateCalculator;
  private final int limit;

  public HybridQuerySelector(
      ApproximateCalculator approximateCalculator,
      int limit) {
    this.approximateCalculator = approximateCalculator;
    this.limit = limit;
  }

  public Word select(WordList queriesCandidate, WordList remain) {
    return findCandidate(queriesCandidate, remain)
        .words()
        .stream()
        .parallel()
        .map(q ->
            new ScoredQuery(
                evaluationQueryScore(q, queriesCandidate, remain),
                q))
        .sorted(Comparator.comparingDouble(ScoredQuery::score))
        .findFirst()
        .map(ScoredQuery::query)
        .orElseThrow();
  }


  public double evaluationQueryScore(Word query, WordList queriesCandidate, WordList remain) {
    return evaluationQueryScore(query, queriesCandidate, remain, 0);
  }

  private double evaluationQueryScore(
      Word query,
      WordList queriesCandidate,
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
          evaluationRemainScore(queriesCandidate, sub, depth);
    }
    return score;
  }

  private double evaluationRemainScore(WordList queriesCandidate, WordList remain, int depth) {
    if (remain.size() == 1) {
      return 1;
    }
    double minScore = Double.MAX_VALUE;
    for (Word query : findCandidate(queriesCandidate, remain).words()) {
      double score = evaluationQueryScore(query, queriesCandidate, remain, depth + 1);
      minScore = Math.min(minScore, score);
      if (minScore == 1.0d) {
        continue; // 1 is the minimum
      }
    }
    return minScore;
  }

  private WordList findCandidate(WordList queriesCandidate, WordList remain) {
    Map<Word, Double> queryScores = queriesCandidate.words().stream()
        .collect(Collectors.toMap(
            Function.identity(),
            candidate -> approximateCalculator.evaluationQueryScore(candidate, remain)
        ));
    return new WordList(queriesCandidate.wordLength(),
        queryScores.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .limit(limit)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList()));
  }

  record ScoredQuery(double score, Word query) {

  }
}

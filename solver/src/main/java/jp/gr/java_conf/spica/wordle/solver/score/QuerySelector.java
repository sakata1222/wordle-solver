package jp.gr.java_conf.spica.wordle.solver.score;

import java.util.Comparator;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public class QuerySelector {

  Word findEffectiveQuery(WordList queriesCandidate, WordList remain,
      IQueryScoreCalculator calculator) {
    return queriesCandidate.words()
        .stream()
        .parallel()
        .map(q ->
            new ScoredQuery(
                calculator.evaluationQueryScore(q, queriesCandidate, remain),
                q))
        .sorted(Comparator.comparingDouble(ScoredQuery::score))
        .findFirst()
        .map(ScoredQuery::query)
        .orElseThrow();
  }

  record ScoredQuery(double score, Word query) {

  }
}

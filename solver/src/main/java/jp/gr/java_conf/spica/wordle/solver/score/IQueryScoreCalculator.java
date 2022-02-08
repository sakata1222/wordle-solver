package jp.gr.java_conf.spica.wordle.solver.score;

import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public interface IQueryScoreCalculator {

  double evaluationQueryScore(Word query, WordList queriesCandidate, WordList remain);
}

package jp.gr.java_conf.spica.wordle.solver.model;

import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public interface IEvaluationResultSupplier {

  WordMatchingResult matches(Word query);
}

package jp.gr.java_conf.spica.wordle.solver.model;

import java.util.HashSet;
import java.util.Set;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public record KnownCondition(
    FixedLetters fixedLetters,
    ContainedLetters containedLetters,
    UnusedLetters unusedLetters) {

  public KnownCondition(int wordLength) {
    this(new FixedLetters(wordLength), new ContainedLetters(), new UnusedLetters());
  }

  public KnownCondition apply(WordMatchingResult result) {
    FixedLetters fixedLetters = this.fixedLetters.apply(result);
    ContainedLetters containedLetters = this.containedLetters.apply(result);
    UnusedLetters unusedLetters = this.unusedLetters.apply(result);
    return new KnownCondition(fixedLetters, containedLetters, unusedLetters);
  }

  public boolean meetsCondition(Word word) {
    return fixedLetters().meetsCondition(word)
        && containedLetters().meetsCondition(word)
        && unusedLetters().meetsCondition(word);
  }

  public Set<Letter> testedLetters() {
    Set<Letter> set = new HashSet<>();
    set.addAll(fixedLetters.letters());
    set.addAll(containedLetters.letters());
    set.addAll(unusedLetters.letter());
    return set;
  }
}

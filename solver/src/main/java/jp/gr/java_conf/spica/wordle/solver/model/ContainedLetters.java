package jp.gr.java_conf.spica.wordle.solver.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.LetterMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.MatchType;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public record ContainedLetters(Set<Letter> contained) {

  public ContainedLetters(Set<Letter> contained) {
    this.contained = Collections.unmodifiableSet(contained);
  }

  public ContainedLetters() {
    this(Collections.emptySet());
  }

  public ContainedLetters apply(WordMatchingResult result) {
    Set<Letter> copy = new HashSet<>(contained);
    for (LetterMatchingResult r : result.results()) {
      if (!(r.matchType() == MatchType.CONTAINS || r.matchType() == MatchType.MATCH)) {
        // ignore other case
        continue;
      }
      copy.add(r.letter());
    }
    return new ContainedLetters(copy);
  }

  public boolean meetsCondition(Word word) {
    return contained.stream()
        .allMatch(word::contains);
  }

  public Set<Letter> letters() {
    return Set.copyOf(contained);
  }
}

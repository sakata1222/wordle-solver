package jp.gr.java_conf.spica.wordle.solver.model;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.LetterMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.MatchType;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public record UnusedLetters(Set<Letter> letter) {

  public UnusedLetters(Set<Letter> letter) {
    this.letter = Collections.unmodifiableSet(Preconditions.checkNotNull(letter));
  }

  public UnusedLetters() {
    this(Collections.emptySet());
  }

  public UnusedLetters apply(WordMatchingResult result) {
    Set<Letter> copy = new HashSet<>(letter);
    for (LetterMatchingResult r : result.results()) {
      if (r.matchType() != MatchType.NONE) {
        // ignore other case
        continue;
      }
      copy.add(r.letter());
    }
    return new UnusedLetters(copy);
  }

  public boolean meetsCondition(Word word) {
    return letter.stream()
        .allMatch(l -> !word.contains(l));
  }

  public Set<Letter> letters() {
    return Set.copyOf(letter);
  }
}

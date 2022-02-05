package jp.gr.java_conf.spica.wordle.solver.model;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.LetterMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.MatchType;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public record FixedLetters(List<FixedLetter> fixedLetters) {

  public FixedLetters(int wordLength) {
    this(Collections.unmodifiableList(IntStream.range(0, wordLength)
        .<FixedLetter>mapToObj(i -> null)
        .collect(Collectors.toList())));
  }

  public FixedLetters apply(WordMatchingResult result) {
    List<FixedLetter> copy = new ArrayList<>(fixedLetters);

    for (int i = 0; i < copy.size(); i++) {
      LetterMatchingResult r = result.get(i);
      if (r.matchType() != MatchType.MATCH) {
        // ignore other case
        continue;
      }
      if (Objects.nonNull(copy.get(i))) {
        Preconditions.checkArgument(
            copy.get(i).letter().equals(r.letter()));
      }
      copy.set(i, new FixedLetter(i, r.letter()));
    }
    return new FixedLetters(copy);
  }

  public boolean meetsCondition(Word word) {
    Preconditions.checkArgument(fixedLetters.size() == word.length());
    for (int i = 0; i < word.length(); i++) {
      if (Objects.isNull(fixedLetters.get(i))) {
        continue;
      }
      if (!fixedLetters().get(i).letter().equals(word.getLetter(i))) {
        return false;
      }
    }
    return true;
  }

  public Set<Letter> letters() {
    return fixedLetters.stream()
        .filter(Objects::nonNull)
        .map(FixedLetter::letter)
        .collect(Collectors.toSet());
  }
}

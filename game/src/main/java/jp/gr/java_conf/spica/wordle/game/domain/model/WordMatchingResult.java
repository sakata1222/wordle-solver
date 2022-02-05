package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record WordMatchingResult(List<LetterMatchingResult> results) {

  public WordMatchingResult {
    Objects.requireNonNull(Collections.unmodifiableList(results));
  }

  public boolean allMatch() {
    return results.stream()
        .map(LetterMatchingResult::matchType)
        .allMatch(MatchType.MATCH::equals);
  }

  public List<String> matchTypes() {
    return results.stream()
        .map(LetterMatchingResult::matchType)
        .map(MatchType::character)
        .collect(Collectors.toList());
  }

  public LetterMatchingResult get(int index) {
    return results.get(index);
  }

  public String toSimpleString() {
    return String.join("", matchTypes());
  }
}

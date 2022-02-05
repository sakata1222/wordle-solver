package jp.gr.java_conf.spica.wordle.game.domain.model;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record WordSet(int wordLength, Set<Word> words) {

  public WordSet {
    Objects.requireNonNull(words);
    Preconditions.checkArgument(
        words.stream().allMatch(
            word -> word.length() == wordLength));
  }

  public static WordSet empty(int wordLength) {
    return new WordSet(wordLength, Collections.emptySet());
  }

  public boolean contains(Word word) {
    return words.stream().anyMatch(word::equals);
  }

  public WordSet filterSpecified(WordSet condition) {
    return new WordSet(wordLength,
        words.stream()
            .filter(w -> !condition.contains(w))
            .collect(Collectors.toSet()));
  }

  public boolean isNotEmpty() {
    return !words.isEmpty();
  }

  public WordSet add(Word word) {
    Set<Word> copy = new HashSet<>();
    copy.add(word);
    return new WordSet(wordLength, copy);
  }
}

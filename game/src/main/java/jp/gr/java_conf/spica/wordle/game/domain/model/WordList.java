package jp.gr.java_conf.spica.wordle.game.domain.model;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public record WordList(int wordLength, List<Word> words) {

  private static WordMatchCache cache = new WordMatchCache();

  public WordList {
    Objects.requireNonNull(words);
  }

  public static WordList empty(int wordLength) {
    return new WordList(wordLength, Collections.emptyList());
  }

  public boolean contains(Word word) {
    return words.stream().anyMatch(word::equals);
  }

  public Word getSameWord(Word word) {
    return words.stream().filter(word::equals).findFirst().orElseThrow();
  }

  public WordList filterSpecified(WordList condition) {
    return new WordList(wordLength,
        words.stream()
            .filter(w -> !condition.contains(w))
            .collect(Collectors.toList()));
  }

  public Word get() {
    Preconditions.checkState(size() == 1);
    return words.iterator().next();
  }

  public Word random() {
    return words.iterator().next();
  }

  public boolean isEmpty() {
    return words.isEmpty();
  }

  public boolean isNotEmpty() {
    return !words.isEmpty();
  }

  public WordList add(Word word) {
    List<Word> copy = new ArrayList<>(words);
    copy.add(word);
    return new WordList(wordLength, copy);
  }

  public WordList remove(Word word) {
    List<Word> copy = new ArrayList<>(words);
    copy.remove(word);
    return new WordList(wordLength, copy);
  }

  public Map<MatchTypes, WordList> split(Word query) {
    return words.stream()
        .collect(Collectors.groupingBy(w -> w.matchesOnlyType(query)))
        .entrySet()
        .stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> new WordList(wordLength, e.getValue())));
  }

  public List<WordList> splitResult(Word query) {
    return words.stream()
        .collect(Collectors.groupingBy(w -> cache.matches(w, query)))
        .entrySet()
        .stream()
        .map(Map.Entry::getValue)
        .map(l -> new WordList(wordLength, l))
        .collect(Collectors.toList());
  }

  public int size() {
    return words.size();
  }
}

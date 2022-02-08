package jp.gr.java_conf.spica.wordle.solver.model;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

public record LetterFrequencyDictionary(Map<Letter, LetterCount> counts,
                                        List<Letter> frequencyOrdered) {

  public LetterFrequencyDictionary(Map<Letter, LetterCount> counts,
      List<Letter> frequencyOrdered) {
    this.counts = Collections.unmodifiableMap(counts);
    this.frequencyOrdered = Collections.unmodifiableList(frequencyOrdered);
  }

  public LetterFrequencyDictionary(Map<Letter, LetterCount> counts) {
    this(counts,
        counts.entrySet().stream()
            .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Entry::getKey)
            .collect(Collectors.toList()));
  }

  public LetterFrequencyDictionary(WordList wordList) {
    this(wordList.words().stream()
        .map(Word::asLetterList)
        .flatMap(List::stream)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            e -> new LetterCount(e.getValue()))));
  }

  public LetterCount countFrequency(Word word) {
    return counts.entrySet()
        .stream()
        .filter(e -> word.contains(e.getKey()))
        .map(e -> e.getValue())
        .reduce(LetterCount::add)
        .orElseGet(() -> new LetterCount(0));
  }

  public Word findMostFrequentWord(WordList words) {
    Preconditions.checkArgument(words.isNotEmpty());
    return words.words()
        .stream()
        .collect(Collectors.toMap(
            Function.identity(),
            w -> countFrequency(w)))
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow();
  }

  public LetterFrequencyDictionary removeTestedLetters(Set<Letter> letters) {
    Map<Letter, LetterCount> copy = new HashMap<>(counts);
    letters.forEach(copy::remove);
    return new LetterFrequencyDictionary(copy);
  }
}

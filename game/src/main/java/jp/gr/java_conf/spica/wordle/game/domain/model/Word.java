package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Word(List<Letter> letters) {

  public Word {
    Objects.requireNonNull(letters);
  }

  public Word(String string) {
    this(string.chars()
        .mapToObj(c -> Character.valueOf((char) c))
        .map(Letter::new)
        .toList());
  }

  public Letter getLetter(int index) {
    return letters.get(index);
  }

  public WordMatchingResult matches(Word other) {
    if (letters.size() != other.letters.size()) {
      throw new IllegalArgumentException("Ths size is different.");
    }
    List<LetterMatchingResult> results = new ArrayList<>();
    for (int i = 0; i < letters.size(); i++) {
      Letter l = letters.get(i);
      Letter otherL = other.letters.get(i);
      if (l.matches(otherL)) {
        results.add(new LetterMatchingResult(otherL, MatchType.MATCH));
      } else if (contains(otherL)) {
        results.add(new LetterMatchingResult(otherL, MatchType.CONTAINS));
      } else {
        results.add(new LetterMatchingResult(otherL, MatchType.NONE));
      }
    }
    return new WordMatchingResult(results);
  }

  public boolean contains(Letter letter) {
    return this.letters.stream().anyMatch(letter::equalsIgnoreCase);
  }

  public int length() {
    return letters.size();
  }

  public String toSimpleString() {
    return letters.stream().map(Letter::letter).collect(Collectors.joining());
  }
}

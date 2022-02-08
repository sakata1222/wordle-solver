package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public record Word(int id, String letters, boolean[] contains) {

  public Word(int id, String letters, boolean[] contains) {
    this.id = id;
    this.letters = letters.toUpperCase(Locale.ENGLISH);
    this.contains = contains;
    for (char c : this.letters.toCharArray()) {
      contains[c - 'A'] = true;
    }
  }

  public Word(int id, String letters) {
    this(id, letters, new boolean['Z' - 'A' + 1]);
  }

  public Letter getLetter(int index) {
    return new Letter(letters.charAt(index));
  }

  public List<Letter> asLetterList() {
    List<Letter> list = new ArrayList<>();
    for (char c : letters.toCharArray()) {
      list.add(new Letter(c));
    }
    return list;
  }

  public WordMatchingResult matches(Word other) {
    if (letters.length() != other.letters.length()) {
      throw new IllegalArgumentException("Ths size is different.");
    }
    List<LetterMatchingResult> results = new ArrayList<>();
    for (int i = 0; i < letters.length(); i++) {
      char t = letters.charAt(i);
      char o = other.letters.charAt(i);
      if (t == o) {
        results.add(new LetterMatchingResult(new Letter(o), MatchType.MATCH));
      } else if (containsChar(o)) {
        results.add(new LetterMatchingResult(new Letter(o), MatchType.CONTAINS));
      } else {
        results.add(new LetterMatchingResult(new Letter(o), MatchType.NONE));
      }
    }
    return new WordMatchingResult(results);
  }

  public MatchTypes matchesOnlyType(Word other) {
    if (letters.length() != other.letters.length()) {
      throw new IllegalArgumentException("Ths size is different.");
    }
    List<MatchType> results = new ArrayList<>();
    for (int i = 0; i < letters.length(); i++) {
      char t = letters.charAt(i);
      char o = other.letters.charAt(i);
      if (t == o) {
        results.add(MatchType.MATCH);
      } else if (containsChar(o)) {
        results.add(MatchType.CONTAINS);
      } else {
        results.add(MatchType.NONE);
      }
    }
    return new MatchTypes(results);
  }

  private boolean containsChar(char c) {
    return contains[c - 'A'];
  }

  public boolean contains(Letter letter) {
    return containsChar(letter.letter());
  }

  public int length() {
    return letters.length();
  }

  public String toSimpleString() {
    return letters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Word word = (Word) o;
    return Objects.equals(letters, word.letters) && Arrays.equals(contains,
        word.contains);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(letters);
    result = 31 * result + Arrays.hashCode(contains);
    return result;
  }
}

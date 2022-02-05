package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record AllWord(WordSet allWords) {

  public AllWord {
    Objects.requireNonNull(allWords);
  }

  public Word random() {
    List<Word> copy = new ArrayList<>(allWords.words());
    Collections.shuffle(copy);
    return copy.get(0);
  }

  public boolean contains(Word word) {
    return allWords.contains(word);
  }

  public WordSet excludeSpecified(WordSet condition) {
    return allWords.filterSpecified(condition);
  }

  public int wordLength() {
    return allWords.wordLength();
  }
}

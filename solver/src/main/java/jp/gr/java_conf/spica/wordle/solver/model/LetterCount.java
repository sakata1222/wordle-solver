package jp.gr.java_conf.spica.wordle.solver.model;

import com.google.common.base.Preconditions;

public record LetterCount(long count) implements Comparable<LetterCount> {

  public LetterCount {
    Preconditions.checkArgument(count >= 0);
  }

  @Override
  public int compareTo(LetterCount o) {
    return Long.compare(count, o.count);
  }

  public LetterCount add(LetterCount letterCount) {
    return new LetterCount(count + letterCount.count);
  }
}

package jp.gr.java_conf.spica.wordle.game.domain.model;

import java.util.Arrays;
import java.util.List;

public record MatchTypes(MatchType[] matchTypeList) {

  public MatchTypes(List<MatchType> matchTypeList) {
    this(matchTypeList.toArray(new MatchType[matchTypeList.size()]));
  }

  public int intValue() {
    int value = 0;
    for (int i = 0; i < matchTypeList.length; i++) {
      value += matchTypeList[i].ordinal() * (i + 1) * 10;
    }
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchTypes that = (MatchTypes) o;
    return Arrays.equals(matchTypeList, that.matchTypeList);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(matchTypeList);
  }
}

package jp.gr.java_conf.spica.wordle.game.domain.model;

public class WordMatchCache {

  private static final int MAX = 13000;
  private int cache[][];

  public WordMatchCache() {
    cache = new int[MAX][MAX];
    for (int i = 0; i < MAX; i++) {
      for (int j = 0; j < MAX; j++) {
        cache[i][j] = -1;
      }
    }
  }

  public int matches(Word src, Word query) {
    int v = cache[src.id()][query.id()];
    if (v != -1) {
      return v;
    }
    cache[src.id()][query.id()] = src.matches(query).matchTypes().intValue();
    return cache[src.id()][query.id()];
  }
}

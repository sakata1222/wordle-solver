package jp.gr.java_conf.spica.wordle.game.domain.model;

public interface IWordleStrategy {

  void init(AllWord allWord);

  Word answer(int remain);

  void feedback(Word word, WordMatchingResult result);
}

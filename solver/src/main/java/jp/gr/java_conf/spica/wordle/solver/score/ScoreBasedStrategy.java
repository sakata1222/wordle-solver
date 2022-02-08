package jp.gr.java_conf.spica.wordle.solver.score;

import java.io.PrintStream;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public class ScoreBasedStrategy implements IWordleStrategy {

  private final PrintStream ps;
  private final HybridQuerySelector querySelector;
  private AllWord allWord;
  private WordList queryCandidate;
  private WordList remainWordList;

  public ScoreBasedStrategy() {
    this(new HybridQuerySelector(new ApproximateCalculator(), 10));
  }

  public ScoreBasedStrategy(
      HybridQuerySelector querySelector) {
    this.ps = System.out;
    this.querySelector = querySelector;
  }

  @Override
  public void init(AllWord allWord) {
    this.allWord = allWord;
    this.queryCandidate = allWord.allWords();
    this.remainWordList = allWord.allWords();
  }

  @Override
  public Word answer(int remain) {
    ps.println("remain size:" + remainWordList.size());
    if (remainWordList.size() == 1) {
      return remainWordList.get();
    }
    if (remainWordList.size() <= remain) {
      return remainWordList.random();
    }
    Word answer = querySelector.select(queryCandidate, remainWordList);
    ps.println(answer);
    return answer;
  }

  @Override
  public void feedback(Word word, WordMatchingResult result) {
    remainWordList = remainWordList.remove(word).split(word).get(result.matchTypes());
    queryCandidate = queryCandidate.remove(word);
    ps.println("remainWords:" + remainWordList);
  }
}

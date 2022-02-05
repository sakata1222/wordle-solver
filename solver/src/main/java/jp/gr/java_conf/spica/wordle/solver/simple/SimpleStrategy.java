package jp.gr.java_conf.spica.wordle.solver.simple;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordSet;
import jp.gr.java_conf.spica.wordle.solver.model.KnownCondition;
import jp.gr.java_conf.spica.wordle.solver.model.LetterFrequencyDictionary;

public class SimpleStrategy implements IWordleStrategy {

  private final PrintStream ps;
  private AllWord allWord;
  private WordSet answered;
  private LetterFrequencyDictionary letterFrequencyDictionary;
  private KnownCondition condition;

  public SimpleStrategy() {
    this.ps = System.out;
  }

  @Override
  public void init(AllWord allWord) {
    this.allWord = allWord;
    this.answered = WordSet.empty(allWord.wordLength());
    this.letterFrequencyDictionary = new LetterFrequencyDictionary(allWord.allWords());
    this.condition = new KnownCondition(allWord.wordLength());
  }

  @Override
  public Word answer(int remain) {
    Word word = decideAnswer(remain);
    ps.println("answer:" + word.toSimpleString());
    return word;
  }

  private Word decideAnswer(int remain) {
    List<Word> validCandidate = wordMeetsCondition();
    if (remain == 1) {
      return validCandidate.get(0);
    }
    if (remain >= validCandidate.size()) {
      return validCandidate.get(0);
    }
    return findUnusedMostFrequentWord();
  }

  private List<Word> wordMeetsCondition() {
    return allWord.excludeSpecified(answered)
        .words()
        .stream()
        .filter(condition::meetsCondition)
        .collect(Collectors.toList());
  }

  private Word findUnusedMostFrequentWord() {
    return letterFrequencyDictionary.findMostFrequentWord(allWord.excludeSpecified(answered));
  }

  @Override
  public void feedback(Word word, WordMatchingResult result) {
    ps.println(result.toSimpleString());
    answered = answered.add(word);
    condition = condition.apply(result);
    letterFrequencyDictionary = new LetterFrequencyDictionary(
        allWord.excludeSpecified(answered)).removeTestedLetters(condition.testedLetters());
  }
}

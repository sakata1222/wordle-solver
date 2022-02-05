package jp.gr.java_conf.spica.wordle.solver.human;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Collectors;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordleStrategy;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;

public class SystemIoStrategy implements IWordleStrategy {

  private final Scanner scanner;
  private final PrintStream ps;
  private AllWord allWord;

  public SystemIoStrategy() {
    this(new Scanner(System.in), System.out);
  }

  public SystemIoStrategy(Scanner scanner, PrintStream ps) {
    this.scanner = scanner;
    this.ps = ps;
  }

  @Override
  public void init(AllWord allWord) {
    this.allWord = allWord;
  }

  @Override
  public Word answer(int remain) {
    ps.println();
    Word word = scan();
    ps.println("Remain :" + remain);
    while (!allWord.contains(word)) {
      ps.println("Your answer is not in the dictionary");
      word = scan();
    }
    return word;
  }

  private Word scan() {
    ps.println("Please input your answer");
    return new Word(scanner.next());
  }

  @Override
  public void feedback(Word word, WordMatchingResult result) {
    ps.println(result.matchTypes().stream().collect(Collectors.joining("")));
  }
}

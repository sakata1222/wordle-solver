package jp.gr.java_conf.spica.wordle.solver.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import jp.gr.java_conf.spica.wordle.game.domain.model.Letter;
import jp.gr.java_conf.spica.wordle.game.domain.model.LetterMatchingResult;
import jp.gr.java_conf.spica.wordle.game.domain.model.MatchType;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordMatchingResult;
import jp.gr.java_conf.spica.wordle.solver.model.IEvaluationResultSupplier;

public class SystemIoEvaluationResultSupplier implements IEvaluationResultSupplier {

  private final BufferedReader br;
  private final PrintStream ps;

  public SystemIoEvaluationResultSupplier() {
    this.br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    this.ps = System.out;
  }

  @Override
  public WordMatchingResult matches(Word query) {
    try {
      ps.println("The query is " + query);
      String line;
      while (!validate(line = br.readLine(), query.length())) {
        ps.println("Format error");
      }
      int i = 0;
      List<LetterMatchingResult> list = new ArrayList<>();
      for (Letter letter : query.asLetterList()) {
        char m = line.charAt(i);
        MatchType type = MatchType.valueOf(m);
        i++;
        list.add(new LetterMatchingResult(letter, type));
      }
      return new WordMatchingResult(list);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean validate(String line, int length) {
    if (line.length() != length) {
      return false;
    }
    for (int i = 0; i < line.length(); i++) {
      if (!MatchType.isValid(line.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}

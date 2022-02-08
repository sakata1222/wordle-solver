package jp.gr.java_conf.spica.wordle.game.domain.file;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Singleton;
import jp.gr.java_conf.spica.wordle.game.domain.model.AllWord;
import jp.gr.java_conf.spica.wordle.game.domain.model.IWordRepository;
import jp.gr.java_conf.spica.wordle.game.domain.model.Word;
import jp.gr.java_conf.spica.wordle.game.domain.model.WordList;

@Singleton
public class WordFileRepository implements IWordRepository {

  @Override
  public AllWord allWords() {
    AtomicInteger idCounter = new AtomicInteger();
    try (Stream<String> lines = Files.lines(
        Path.of(this.getClass().getResource("/word-list.txt").toURI()))) {
      return new AllWord(
          new WordList(
              5,
              lines
                  .distinct()
                  .map(l -> new Word(idCounter.getAndIncrement(), l))
                  .collect(Collectors.toList())));
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jp.gr.java_conf.spica.wordle.app;

import jp.gr.java_conf.spica.wordle.list.LinkedList;

import static jp.gr.java_conf.spica.wordle.utilities.StringUtils.join;
import static jp.gr.java_conf.spica.wordle.utilities.StringUtils.split;
import static jp.gr.java_conf.spica.wordle.app.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
    }
}
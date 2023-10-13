package comprehensive;

import java.util.Arrays;

/**
 * This class will generate a number of random phases.
 * Each phrase is constructed through a given grammar file and
 * will be dependent on its contents.
 *
 * @author Tommy Shelburne & Logan Munier
 * @version Aug 3, 2022
 */
public class RandomPhraseGenerator {
    public static void main(String[] args) {
        String filename = args[0];
        int numOfSentences = Integer.parseInt(args[1]);

        Grammar grammar = new Grammar(filename);

        for (int i = 0; i < numOfSentences; i++) {
            String sentence = grammar.generate();
            System.out.println(sentence);
        }
    }
}

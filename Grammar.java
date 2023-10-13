package comprehensive;

import com.sun.jdi.StringReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class represents a grammar object.
 *
 * @author Tommy Shelburne & Logan Munier
 * @version Aug 3, 2022
 */
public class Grammar {

	private HashMap<String, ArrayList<String>> data;

	private final Random RNG;

	/**
	 * Creates an empty grammar.
	 */
	public Grammar() {
		this.data = new HashMap<>();
		this.RNG = new Random();
	}

	/**
	 * Creates a grammar from a valid file.
	 *
	 * @param filename - path of valid grammar file
	 */
	public Grammar(String filename) {
		this();
		buildGrammar(filename);
	}

	/**
	 * Parses a valid grammar file which follows strict formatting rules.
	 * Maps each non-terminal with its corresponding productions.
	 *
	 * @param filename - path of valid grammar file
	 */
	private void buildGrammar(String filename) {
		try {
			Scanner fileIn = new Scanner(new File(filename));
//			fileIn.useDelimiter("[{|}]");
			while (fileIn.hasNextLine()) {
				String line = fileIn.nextLine();
				// read each line the file until "{" is reached
				if (line.equals("{")) {
					// the first line inside the {} will always be a non-terminal
					String nonTerm = fileIn.nextLine();
					ArrayList<String> productions = new ArrayList<>();

					// collect productions of current non-terminal
					String currentRule = fileIn.nextLine();
					while (!currentRule.equals("}")) {
						productions.add(currentRule);
						currentRule = fileIn.nextLine();
					}
					// link productions with the non-terminal
					data.put(nonTerm, productions);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

	/**
	 * Driver method to initiate sentence generation.
	 * < start> is passed as the start symbol.
	 *
	 * @return the generated sentence
	 */
	public String generate() {
		StringBuilder sentenceBuilder = new StringBuilder();
		Stack<String> sentenceStack = new Stack<>();
		return generate("<start>", sentenceStack, sentenceBuilder);
	}

	/**
	 * Continually works through this grammar until all non-terminals have been
	 * turned into terminals through various productions.
	 *
	 * @param nonTerm - the current non-terminal
	 * @param sentenceStack - the stack holding the working sentence data (non-terminals and terminals)
	 * @param sentenceBuilder - the working sentence (only terminals)
	 * @return the final generated sentence
	 */
	public String generate(String nonTerm, Stack<String> sentenceStack, StringBuilder sentenceBuilder) {
		try {
			// retrieve a random production from given non-terminal
			String production = getRandomProduction(nonTerm);

			// split up data for stack implementation
			ArrayList<String> splitProduction = split(production);
			for (int i = splitProduction.size() - 1; i >= 0; i--) {
				sentenceStack.push(splitProduction.get(i));
			}

			while (!sentenceStack.isEmpty()) {
				String currentItem = sentenceStack.pop();
				if (currentItem.startsWith("<")) {
					// we have a non-terminal
					return generate(currentItem, sentenceStack, sentenceBuilder);
				}
				sentenceBuilder.append(currentItem);
			}
		} catch (StackOverflowError e) {
			System.out.println("StringBuilder is out of memory\nFinal sentence generated: ");
			return sentenceBuilder.toString();
		}
		return sentenceBuilder.toString();
	}

	/**
	 * Retrieves a random production from the given non-terminal.
	 *
	 * @param nonTerm - valid non-terminal
	 * @return a random production
	 * @throws IllegalArgumentException if this grammar does not contain any productions
	 * 		for the given non-terminal
	 */
	private String getRandomProduction(String nonTerm) {
		ArrayList<String> productions = data.get(nonTerm);
		if (productions == null) {
			throw new IllegalArgumentException("No productions found for non-terminal: " + nonTerm);
		}
		int randomIndex = RNG.nextInt(productions.size());
		return productions.get(randomIndex);
	}

	/**
	 * Splits a given production string such that non-terminals and terminals
	 * are separated from each other. Spaces and punctuation are also accounted for.
	 *
	 * @param production - a string representing the given production
	 * @return a list containing individual non-terminals and terminals
	 */
	private ArrayList<String> split(String production) {
		ArrayList<String> splitSentence = new ArrayList<>();
		StringBuilder newString = new StringBuilder();
		for (int i = 0; i < production.length(); i++) {
			char currentChar = production.charAt(i);
			if (currentChar == '<' && !newString.isEmpty()) {
				splitSentence.add(newString.toString());
				newString = new StringBuilder();
				newString.append(currentChar);
				continue;
			}
			newString.append(currentChar);
			if (currentChar == ' ' || currentChar == '>' || i == production.length()-1) {
				splitSentence.add(newString.toString());
				newString = new StringBuilder();
			}
		}
		return splitSentence;
	}

	/**
	 * add a new production rule to a given production value
	 * @param pVal - the production value being added to
	 * @param rule - the production rule being added to pVal
	 */
	public void addNonTerminal (String pVal, String rule) {
		// get a reverence to the pVal in data
		ArrayList<String> production = data.get(pVal);

		// add the rule to the production value
		production.add(rule);
	}
}

package comprehensive;

/**
 * This class contains code for collecting the running times of 
 * the grammar implemented in this package.
 * 
 * @author Tommy Shelburne
 * @version August 3, 2022
 */
public class Timer {
	public static void main(String[] args) {
//		timeIncreaseProductions();
		timeGeneratedSentences();
	}
	
	public static void timeIncreaseProductions() {
		long startTime, midpointTime, stopTime;

		// First, spin computations until one second has gone by.
		// This allows this thread to stabilize.

		startTime = System.nanoTime();
		while (System.nanoTime() - startTime < 1000000000) { // empty block, 1 second total
		}

		// Collect running times.

		// Use a large value since running times will be small.
		int timesToLoop = 10000;
		
		// For each problem size n . . .
		for (int probSize = 100; probSize <= 2000; probSize += 100) {
			Grammar grammar = new Grammar("src/comprehensive/grammar_files/abc_simple.g");
			for (int i = 0; i < probSize; i++)
				grammar.addNonTerminal("<a>", "<a> a");

			startTime = System.nanoTime();

			// Run timeToLoop times.
			for (int i = 0; i < timesToLoop; i++) {
//				System.out.println(grammar.generate());
				grammar.generate();
			}

			midpointTime = System.nanoTime();

			// Run an empty loop to capture the cost of running the loop.
			for (int i = 0; i < timesToLoop; i++) {
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop.
			// Average it over the number of runs.

			long averageTime = ((midpointTime - startTime) - 
					(stopTime - midpointTime)) / timesToLoop;

//			System.out.println(averageTime);
			System.out.println(probSize + "\t" + averageTime + "\t");
			System.out.println(grammar.generate());
		}
	}

	public static void timeGeneratedSentences() {
		long startTime, midpointTime, stopTime;

		// First, spin computations until one second has gone by.
		// This allows this thread to stabilize.

		startTime = System.nanoTime();
		while (System.nanoTime() - startTime < 1000000000) { // empty block, 1 second total
		}

		// Collect running times.

		// Use a large value since running times will be small.
		int timesToLoop = 100;

		// For each problem size n . . .
		Grammar grammar = new Grammar("src/comprehensive/grammar_files/assignment_extension_request.g");
		for (int probSize = 100; probSize <= 2000; probSize += 100) {

			startTime = System.nanoTime();

			// Run timeToLoop times.
			for (int i = 0; i < timesToLoop; i++) {
				for (int j = 0; j < probSize; j++) {
					grammar.generate();
				}
			}

			midpointTime = System.nanoTime();

			// Run an empty loop to capture the cost of running the loop.
			for (int i = 0; i < timesToLoop; i++) {
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop.
			// Average it over the number of runs.

			long averageTime = ((midpointTime - startTime) -
					(stopTime - midpointTime)) / timesToLoop;
			averageTime /= probSize;

//			System.out.println(averageTime);
			System.out.println(probSize + "\t" + averageTime + "\t");
		}
	}
	
}
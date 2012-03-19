package artificialNN;

public class Tester {
	UtilClass util;
	Dataset ds;
	DataItem[] testItems;

	/**
	 * constructor
	 * 
	 * @param util
	 */
	public Tester(UtilClass util) {
		this.util = util;
		this.ds = util.dataset;
		this.testItems = ds.testItems;
	}

	/**
	 * tests the accuracy of the network
	 */
	public void test(DataItem[] items) {
		int correct = 0, incorrect = 0;
		for (DataItem item : items) {
			boolean pred = true;
			item.print(ds.attrSet.inputs[0].cont);
			double[][] res = util.annLearner.propagateInput(item);
			print("Hidden Values: ", res[0]);
			System.out.print("Network output:");
			for (int i = 0; i < res[1].length; i++) {
				System.out.print(" " + (res[1][i] >= 0.5 ? 1 : 0) + " ");
				if (item.outputVec[i] != (res[1][i] >= 0.5 ? 1 : 0)) {
					pred = false;
				}
			}
			System.out.println("\n");
			if (pred) {
				correct++;
			} else {
				incorrect++;
			}
		}
		System.out.println("Correct Predictions: " + correct);
		System.out.println("Incorrect Predictions: " + incorrect);
		System.out.println("Accuracy: "
				+ ((float) correct / (correct + incorrect)));
	}

	/**
	 * prints an array of double values
	 * 
	 * @param str
	 * @param ds2
	 */
	private void print(String str, double[] ds2) {
		System.out.print(str);
		for (int i = 1; i < ds2.length; i++) {
			System.out.printf("%6.4f ", ds2[i]);
		}
		System.out.println();
	}

	/**
	 * tests the accuracy of network on noisy iris (differs from test method in
	 * report size)
	 * 
	 * @param testItems2
	 */
	public float testNoisy(DataItem[] items, boolean usingValidation,
			boolean print) {
		int correct = 0, incorrect = 0;
		for (DataItem item : items) {
			boolean pred = true;
			double[][] res = util.annLearner.propagateInput(item);
			for (int i = 0; i < res[1].length; i++) {
				if (item.outputVec[i] != (res[1][i] >= 0.5 ? 1 : 0)) {
					pred = false;
				}
			}
			if (pred) {
				correct++;
			} else {
				incorrect++;
			}
		}
		if (print) {
			print4testNoisy(usingValidation, correct, incorrect);
		}
		return ((float) correct / (correct + incorrect));
	}

	/**
	 * prints testNoisy report
	 * 
	 * @param usingValidation
	 * @param correct
	 * @param incorrect
	 */
	private void print4testNoisy(boolean usingValidation, int correct,
			int incorrect) {
		if (usingValidation) {
			System.out.print("Using validation set: ");
		} else {
			System.out.print("Without validation set: ");
		}
		System.out.print("Correct: " + correct);
		System.out.print("   Incorrect: " + incorrect);
		System.out.println("   Accuracy: "
				+ ((float) correct / (correct + incorrect)));
	}
}

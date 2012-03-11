package artificialNN;

import java.util.Arrays;

public class Tester {
	UtilClass util;
	Dataset ds;
	DataItem[] testItems;

	public Tester(UtilClass util) {
		this.util = util;
		this.ds = util.dataset;
		this.testItems = ds.testItems;
	}

	/**
	 * tests the accuracy of rulese producted from tree
	 * 
	 * @param testItems2
	 */
	public void test(DataItem[] items) {
		int correct = 0, incorrect = 0;
		int[] prediction = new int[ds.attrSet.outputVecSize];
		for (DataItem item : items) {
			boolean pred = true;
			item.print(ds.attrSet.inputs[0].cont);
			double[][] res = util.annLearner.propagateInput(item);
			for (int i = 0; i < res[1].length; i++) {
				System.out.print(" " + (res[1][i] >= 0.5 ? 1 : 0) + ",");
				if (item.outputVec[i] != (res[1][i] >= 0.5 ? 1 : 0)) {
					pred = false;
				}
			}
			System.out.println();
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
}

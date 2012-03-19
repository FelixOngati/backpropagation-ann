package artificialNN;

import artificialNN.Dataset;
import artificialNN.Form;

public class UtilClass {
	Form form;
	Dataset dataset;
	ANNLearner annLearner;

	/**
	 * quits the program
	 */
	public void quit() {
		System.exit(0);
	}

	/**
	 * reads the data from files
	 */
	public void readData() {
		String name = form.getParams().ds;
		dataset = new Dataset(name, this);
		System.out.println("Attribute, tarin and test sets are loaded ("
				+ dataset.allTrainItems.length + ", "
				+ dataset.testItems.length + ").");
	}

	/**
	 * prints the train and test sets on the screen
	 */
	public void printData() {
		dataset.attrSet.print();
		System.out.println("Train Items:");
		dataset.printDataItems(true);
		System.out.println("Test Items:");
		dataset.printDataItems(false);
	}

	/**
	 * add noises to iris dataset and lears and tests it (from 1% to 15%)
	 */
	public void testANNonNoisyIris() {
		form.setParams4TestNoisyIris();
		int valSize = form.getParams().validationSize;
		System.out.println("Testing Network on Noisy Iris Dataset...");
		System.out.println("Data Loaded.");
		for (int i = 1; i <= 40; i += 2) {
			dataset = new Dataset("iris", this);
			dataset.corruptData(i);
			System.out.println(i * dataset.allTrainItems.length / 100
					+ " records of data is corrupted.");
			Tester tester = new Tester(this);
			annLearner = new ANNLearner(this);
			annLearner.learn();
			tester.testNoisy(dataset.testItems, false, true);
			dataset.splitTrainItems(valSize);
			annLearner.learnAndValidate();
			tester.testNoisy(dataset.testItems, true, true);
			System.out.println();
		}
	}

	/**
	 * splits the dataset to train and validation set
	 */
	public void split2TrainAndValidation() {
		dataset.splitTrainItems(10);
		System.out.println("Splited to " + dataset.trainItems.length
				+ " train items and " + dataset.validItems.length
				+ " validation items.");
	}

	/**
	 * creats a learner object and calls it to learn
	 */
	public void learnANN() {
		System.out.println("\nStarting Learning...");
		annLearner = new ANNLearner(this);
		annLearner.learn();
		System.out.println("\nFinished Learning...");
	}

	/**
	 * tests the learned network against test items
	 */
	public void testANN() {
		System.out.println("\nStarting Testing...");
		Tester tester = new Tester(this);
		tester.test(dataset.testItems);
		System.out.println("\nFinished Testing...");
	}

	/**
	 * main function of the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UtilClass util = new UtilClass();
		util.form = new Form(util);
	}

}

package artificialNN;

import artificialNN.Dataset;
import artificialNN.Form;

public class UtilClass {
	Form form;
	Dataset dataset;
	ANNLearner annLearner;

	public void quit() {
		System.exit(0);
	}

	public void readData() {
		String name = form.getParams().ds;
		dataset = new Dataset(name, this);
		System.out.println("Attribute, tarin and test sets are loaded ("
				+ dataset.allTrainItems.length + ", "
				+ dataset.testItems.length + ").");
	}

	public void printData() {
		dataset.attrSet.print();
		dataset.printDataItems(true);
		dataset.printDataItems(false);
	}

	public void corruptData() {
		dataset.corruptData();
		System.out.println(form.getParams().corruptionPercent
				* dataset.allTrainItems.length / 100
				+ " records of data corrupted.");
	}

	public void split2TrainAndValidation() {
		dataset.splitTrainItems();
		System.out.println("Splited to " + dataset.trainItems.length
				+ " train items and " + dataset.validItems.length
				+ " validation items.");
	}

	public void learnANN() {
		System.out.println("\nStarting Learning...");
		annLearner = new ANNLearner(this);
		annLearner.learn();
		System.out.println("\nFinished Learning...");
	}

	public void testANN() {
		System.out.println("\nStarting Testing...");
		Tester tester = new Tester(this);
		tester.test(dataset.testItems);
		System.out.println("\nFinished Testing...");
	}

	public static void main(String[] args) {
		UtilClass util = new UtilClass();
		util.form = new Form(util);
	}

}

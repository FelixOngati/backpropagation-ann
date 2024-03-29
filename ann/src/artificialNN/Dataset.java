package artificialNN;

import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

class Dataset {
	AttributeSet attrSet;
	DataItem[] allTrainItems = {};
	DataItem[] trainItems = {};
	DataItem[] validItems = {};
	DataItem[] testItems = {};
	boolean PRINT = false;
	UtilClass util;

	/**
	 * constructor
	 * 
	 * @param dsName
	 * @param util
	 */
	public Dataset(String dsName, UtilClass util) {
		this.util = util;
		attrSet = new AttributeSet(dsName);
		loadDataItems(dsName, true);
		loadDataItems(dsName, false);
	}

	/**
	 * loads data items from attribute, train and test files
	 * 
	 * @param fName
	 * @param train
	 */
	private void loadDataItems(String fName, boolean train) {
		Scanner sc = null;
		String trainOrTest = train ? "-train.txt" : "-test.txt";
		ArrayList<DataItem> tempAL = new ArrayList<DataItem>();
		try {
			FileReader fr = new FileReader("data/" + fName + trainOrTest);
			sc = new Scanner(new BufferedReader(fr));
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
		while (sc.hasNext()) {
			tempAL.add(new DataItem(sc.nextLine(), attrSet));
		}
		if (train) {
			allTrainItems = tempAL.toArray(allTrainItems);
			trainItems = allTrainItems;
		} else {
			testItems = tempAL.toArray(testItems);
		}
	}

	/**
	 * print all data items in train or test set
	 * 
	 * @param train
	 */
	void printDataItems(boolean train) {
		DataItem[] items = train ? allTrainItems : testItems;
		for (DataItem item : items) {
			item.print(attrSet.inputs[0].cont);
		}
		System.out.println();
	}

	/**
	 * splits train data items into train and validation set
	 * 
	 * @param validationPercent
	 */
	public void splitTrainItems(int validationPercent) {
		java.util.Random rand = new Random(util.form.getParams().randomSeed);
		int numOfValidationItems = (allTrainItems.length * validationPercent) / 100;
		trainItems = new DataItem[allTrainItems.length - numOfValidationItems];
		validItems = new DataItem[numOfValidationItems];
		boolean[] selected = new boolean[allTrainItems.length];
		int i = 0;
		while (i < numOfValidationItems) {
			int r = rand.nextInt(allTrainItems.length);
			if (selected[r] == false) {
				selected[r] = true;
				validItems[i++] = allTrainItems[r];
			}
		}
		int j = 0;
		for (i = 0; i < allTrainItems.length; i++) {
			if (selected[i] == false) {
				trainItems[j++] = allTrainItems[i];
			}
		}
		System.out
				.println(validationPercent + "% of data (" + validItems.length
						+ " items) is getting used for validation.");
	}

	/**
	 * corrupts some of the records in dataset
	 * 
	 * @param corruptionPercent
	 */
	public void corruptData(int corruptionPercent) {
		String[] vals = attrSet.outputs[0].vals;
		java.util.Random rand = new Random(util.form.getParams().randomSeed * 2);
		int num2Corrupt = (allTrainItems.length * corruptionPercent) / 100;
		int i = 0;
		boolean[] selected = new boolean[allTrainItems.length];
		while (i < num2Corrupt) {
			int r = rand.nextInt(allTrainItems.length);
			if (selected[r] == false) {
				selected[r] = true;
				while (true) {
					int r2 = rand.nextInt(vals.length);
					if (!allTrainItems[r].outputs[0].equals(vals[r2])) {
						allTrainItems[r].outputs[0] = vals[r2];
						allTrainItems[r].calcVectors(attrSet);
						i++;
						break;
					}
				}
			}
		}
	}
}

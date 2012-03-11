package artificialNN;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class AttributeSet {
	Attribute[] inputs = {};
	Attribute[] outputs = {};
	int inputVecSize;
	int outputVecSize;

	public AttributeSet(String dsName) {
		ArrayList<Attribute> tempAL = new ArrayList<Attribute>();
		String fName = "data/" + dsName + "-attr.txt";
		Scanner sc = null;
		try {
			sc = new Scanner(new BufferedReader((new FileReader(fName))));
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
		int index = 0;
		boolean input = true;
		while (sc.hasNext()) {
			String str = sc.nextLine();
			if (str.length() == 0) {
				inputs = tempAL.toArray(inputs);
				tempAL = new ArrayList<Attribute>();
				input = false;

			} else {
				tempAL.add(new Attribute(str, index++, input));
			}
		}
		outputs = tempAL.toArray(outputs);
		calcInOutSize();
	}

	private void calcInOutSize() {
		int i = 0;
		if (inputs[0].cont == true) {
			i = inputs.length;
		} else {
			for (Attribute attr : inputs) {
				i += attr.vals.length;
			}
		}
		inputVecSize = i;

		i = 0;
		for (Attribute attr : outputs) {
			i += attr.vals.length;
		}
		outputVecSize = i;
	}

	public void print() {
		for (Attribute attr : inputs) {
			attr.print();
		}
		System.out.println("input vector size:" + inputVecSize);
		for (Attribute attr : outputs) {
			attr.print();
		}
		System.out.println("output vector size:" + outputVecSize + "\n");
	}
}

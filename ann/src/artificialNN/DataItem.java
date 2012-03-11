package artificialNN;

import java.util.Scanner;
import java.util.Arrays;

class DataItem {
	String[] fields;
	String[] inputs;
	String[] outputs;
	int[] inputVec;
	int[] outputVec;
	double[] dInputVec;

	boolean testResult;

	public DataItem(String line, AttributeSet as) {
		init(as);
		Scanner sc = new Scanner(line);
		int i = 0;
		while (i < as.inputs.length) {
			inputs[i] = sc.next();
			fields[i] = inputs[i++];
		}
		int j = i;
		i = 0;
		while (i < as.outputs.length) {
			outputs[i] = sc.next();
			fields[j++] = outputs[i++];
		}
		calcVectors(as);
	}

	private void init(AttributeSet as) {
		inputs = new String[as.inputs.length];
		outputs = new String[as.outputs.length];
		fields = new String[as.inputs.length + as.outputs.length];
		inputVec = new int[as.inputVecSize + 1];
		dInputVec = new double[as.inputVecSize + 1];
		outputVec = new int[as.outputVecSize];
		testResult = false;
	}

	public void calcVectors(AttributeSet as) {
		if (as.inputs[0].cont) {
			dInputVec[0] = 1;
			for (int i = 1; i <= inputs.length; i++) {
				dInputVec[i] = Double.parseDouble(inputs[i]);
			}
		} else {
			inputVec[0] = 1;
			int k = 1;
			for (int i = 0; i < inputs.length; i++) {
				for (int j = 0; j < as.inputs[i].vals.length; j++) {
					if (inputs[i].equals(as.inputs[i].vals[j])) {
						inputVec[k + j] = 1;
						break;
					}
				}
				k += as.inputs[i].vals.length;
			}
		}
		int k = 0;
		for (int i = 0; i < outputs.length; i++) {
			for (int j = 0; j < as.outputs[i].vals.length; j++) {
				if (outputs[i].equals(as.outputs[i].vals[j])) {
					outputVec[k + j] = 1;
					break;
				}
			}
			k += as.outputs[i].vals.length;
		}
	}

	public void print(boolean cont) {
		// System.out.println(Arrays.toString(fields));
		// System.out.print(Arrays.toString(inputs) + " ");
		if (cont) {
			System.out.print("inVec:\n" + Arrays.toString(dInputVec));
		} else {
			System.out.print("inVec:\n" + Arrays.toString(inputVec));
		}
		// System.out.print(" -- " + Arrays.toString(outputs));
		System.out.println("\noutVec:\n" + (Arrays.toString(outputVec)));
	}
}

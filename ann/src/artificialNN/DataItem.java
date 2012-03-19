package artificialNN;

import java.util.Scanner;
import java.util.Arrays;

class DataItem {
	String[] fields;
	String[] inputs;
	String[] outputs;
	double[] inputVec;
	int[] outputVec;

	/**
	 * constructor
	 * 
	 * @param line
	 * @param as
	 */
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

	/**
	 * initializes the vectors which save the normal and coded version of the
	 * data items
	 * 
	 * @param as
	 */
	private void init(AttributeSet as) {
		inputs = new String[as.inputs.length];
		outputs = new String[as.outputs.length];
		fields = new String[as.inputs.length + as.outputs.length];
	}

	/**
	 * calculates the coded version of data items from the normal data item
	 * 
	 * @param as
	 */
	void calcVectors(AttributeSet as) {
		inputVec = new double[as.inputVecSize + 1];
		outputVec = new int[as.outputVecSize];
		if (as.inputs[0].cont) {
			inputVec[0] = 1;
			for (int i = 1; i <= inputs.length; i++) {
				inputVec[i] = Double.parseDouble(inputs[i - 1]);
			}
		} else {
			inputVec[0] = 1;
			int k = 1;
			for (int i = 0; i < inputs.length; i++) {
				if (as.inputs[i].vals.length > 2) {
					for (int j = 0; j < as.inputs[i].vals.length; j++) {
						if (inputs[i].equals(as.inputs[i].vals[j])) {
							inputVec[k + j] = 1;
							break;
						}
					}
					k += as.inputs[i].vals.length;
				} else {
					inputVec[k++] = (inputs[i].equals(as.inputs[i].vals[0])) ? 0
							: 1;
				}
			}
		}
		int k = 0;
		for (int i = 0; i < outputs.length; i++) {
			if (as.outputs[i].vals.length > 2) {
				for (int j = 0; j < as.outputs[i].vals.length; j++) {
					if (outputs[i].equals(as.outputs[i].vals[j])) {
						outputVec[k + j] = 1;
						break;
					}
				}
				k += as.outputs[i].vals.length;
			} else {
				outputVec[k++] = (outputs[i].equals(as.outputs[i].vals[0])) ? 0
						: 1;
			}
		}
	}

	/**
	 * prints the data item
	 * 
	 * @param cont
	 */
	public void print(boolean cont) {
		System.out.print("input: " + Arrays.toString(inputs));
		System.out.println("   output:" + (Arrays.toString(outputs)));
		print("inVec:", inputVec);
		System.out.println("    outVec:" + (Arrays.toString(outputVec)));
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
			System.out.printf("%5.1f", ds2[i]);
		}
	}

}

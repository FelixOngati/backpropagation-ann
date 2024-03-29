package artificialNN;

import java.util.Arrays;
import java.util.Random;

public class ANNLearner {
	UtilClass util;
	AttributeSet as;
	DataItem[] trainItems;
	double eta;
	double momentum;
	int iter;
	int numofHid;
	double[][] in2hidMat;
	double[][] hid2outMat;
	int seed;
	Random rand;
	boolean printLearningProcess;
	double[] prevDeltaO;
	double[] prevDeltaH;

	/**
	 * constructor of the artificial neural network
	 * 
	 * @param util
	 *            reference to the main util object of the program
	 */
	public ANNLearner(UtilClass util) {
		trainItems = util.dataset.trainItems;
		this.util = util;
		Params p = util.form.getParams();
		this.eta = p.learnRate;
		this.momentum = p.momentum;
		this.numofHid = p.hidUnits;
		this.iter = p.iter;
		as = util.dataset.attrSet;
		this.rand = new Random(p.randomSeed);
		this.printLearningProcess = p.printLearnProcess;

		prevDeltaO = new double[trainItems[0].outputVec.length];
		prevDeltaH = new double[numofHid + 1];
	}

	/**
	 * initializes size of the weight matrices and populate them with small
	 * random values
	 */
	private void initMatrices() {
		in2hidMat = new double[as.inputVecSize + 1][numofHid];
		for (int i = 0; i <= as.inputVecSize; i++) {
			for (int j = 0; j < numofHid; j++) {
				in2hidMat[i][j] = (rand.nextDouble() - 0.5F) / 5;
			}
		}
		hid2outMat = new double[numofHid + 1][as.outputVecSize];
		for (int i = 0; i <= numofHid; i++) {
			for (int j = 0; j < as.outputVecSize; j++) {
				hid2outMat[i][j] = (rand.nextDouble() - 0.5F) / 5;
			}
		}
	}

	/**
	 * prints the weight matrices
	 */
	private void printWeights(double[] hidOut, double[] curOut, DataItem item) {
		if (!printLearningProcess)
			return;
		System.out.println("\ninVec:\n" + Arrays.toString(item.inputVec)
				+ Arrays.toString(item.inputs));
		System.out.println("in-hid weights:");
		for (int i = 0; i <= as.inputVecSize; i++) {
			// System.out.println(Arrays.toString(in2hidMat[i]));
			for (int j = 0; j < in2hidMat[i].length; j++) {
				System.out.printf("%6.3f  ", in2hidMat[i][j]);
			}
			System.out.println();
		}
		// System.out.println("\nhidOut:" + Arrays.toString(hidOut));
		System.out.println("\nhidOut:");
		for (int i = 0; i < hidOut.length; i++) {
			System.out.printf("%6.3f  ", hidOut[i]);
		}
		System.out.println("\nhid-out weights:");
		for (int i = 0; i <= numofHid; i++) {
			// System.out.println(Arrays.toString(hid2outMat[i]));
			for (int j = 0; j < hid2outMat[i].length; j++) {
				System.out.printf("%6.3f  ", hid2outMat[i][j]);
			}
			System.out.println();
		}
		// System.out.println("out:" + Arrays.toString(out));
		System.out.println("\ncurOut:");
		for (int i = 0; i < curOut.length; i++) {
			System.out.printf("%6.3f  ", curOut[i]);
		}
		System.out.println();
	}

	/**
	 * propagates a data item through the ANN
	 * 
	 * @param inVec
	 *            the data item
	 * @return the output vector of the network
	 */
	double[][] propagateInput(DataItem item) {
		double[] inVec = item.inputVec;
		double[] hidOut = new double[numofHid + 1];
		for (int i = 0; i < numofHid; i++) {
			for (int j = 0; j <= as.inputVecSize; j++) {
				hidOut[i + 1] += inVec[j] * in2hidMat[j][i];
			}
			hidOut[i + 1] = (1 / (1 + Math.exp(-hidOut[i + 1])));
		}
		hidOut[0] = 1;

		double[] curOut = new double[as.outputVecSize];
		for (int i = 0; i < as.outputVecSize; i++) {
			for (int j = 0; j <= numofHid; j++) {
				curOut[i] += hidOut[j] * hid2outMat[j][i];
			}
			curOut[i] = (1 / (1 + Math.exp(-curOut[i])));
		}
		printWeights(hidOut, curOut, item);
		double[][] res = new double[2][];
		res[0] = hidOut;
		res[1] = curOut;
		return res;
	}

	/**
	 * updates the weight matrices based on the error we have in forward
	 * propagation
	 * 
	 * @param curOut
	 *            current output vector calculated based on input vector
	 * @param outVec
	 *            real output we have from trainset
	 */
	private void propagateError(double[] curHidOut, double[] curOut,
			int[] outVec, double[] inVec) {
		// calculating delta
		double[] deltaO = new double[outVec.length];
		for (int i = 0; i < deltaO.length; i++) {
			deltaO[i] = curOut[i] * (1 - curOut[i]) * (outVec[i] - curOut[i]);
		}
		double[] deltaH = new double[numofHid + 1];
		for (int i = 0; i < deltaH.length; i++) {
			double sigmaError = 0;
			for (int j = 0; j < outVec.length; j++) {
				sigmaError += hid2outMat[i][j] * deltaO[j];
			}
			deltaH[i] = curHidOut[i] * (1 - curHidOut[i]) * sigmaError;
		}

		printOutputs(curOut, outVec, deltaO, deltaH);

		// updating weights (in2hid)
		for (int i = 0; i <= as.inputVecSize; i++) {
			for (int j = 1; j <= numofHid; j++) {
				double delta = eta * deltaH[j] * inVec[i];
				delta += momentum * prevDeltaH[j];
				in2hidMat[i][j - 1] += delta;
			}
		}
		// hid2out
		for (int i = 0; i <= numofHid; i++) {
			for (int j = 0; j < as.outputVecSize; j++) {
				double delta = eta * deltaO[j] * curHidOut[i];
				delta += momentum * prevDeltaO[j];
				hid2outMat[i][j] += delta;
			}
		}
		prevDeltaO = deltaO;
		prevDeltaH = deltaH;
	}

	/**
	 * prints output generated by the network and the real output, plus the
	 * error values of output and hidden units
	 * 
	 * @param curOut
	 * @param outVec
	 * @param deltaH
	 * @param deltaO
	 */
	private void printOutputs(double[] curOut, int[] outVec, double[] deltaO,
			double[] deltaH) {
		if (!printLearningProcess)
			return;

		System.out.println("\noutVec:");// + Arrays.toString(outVec));
		for (int i = 0; i < outVec.length; i++) {
			System.out.printf("%5d   ", outVec[i]);
		}
		System.out.println("\ncurOut:");
		for (int i = 0; i < curOut.length; i++) {
			System.out.printf("%6.3f  ", curOut[i]);
		}
		System.out.println();

		System.out.println("\nout unit error:");
		for (int i = 0; i < deltaO.length; i++) {
			System.out.printf("%6.3f  ", deltaO[i]);
		}
		System.out.println("\nhidden unit error:");
		for (int i = 0; i < deltaH.length; i++) {
			System.out.printf("%6.3f  ", deltaH[i]);
		}
	}

	/**
	 * main method of the ANN which is called to start learning
	 */
	public void learn() {
		initMatrices();
		int i = 0;
		while (i++ < iter) {
			for (DataItem item : trainItems) {
				double[] inVec = item.inputVec;
				int[] outVec = item.outputVec;
				double[][] curOutandHidOut = propagateInput(item);
				propagateError(curOutandHidOut[0], curOutandHidOut[1], outVec,
						inVec);
			}
		}
	}

	/**
	 * method of the ANN to learn and setting the best weights based on
	 * validation set.
	 */
	public void learnAndValidate() {
		initMatrices();
		int i = 0;
		float accuracy = -1;
		double[][] tempIn2hidMat = new double[as.inputVecSize + 1][numofHid];
		double[][] tempHid2outMat = new double[numofHid + 1][as.outputVecSize];

		while (i++ < iter) {
			for (DataItem item : trainItems) {
				double[] inVec = item.inputVec;
				int[] outVec = item.outputVec;
				double[][] curOutandHidOut = propagateInput(item);
				propagateError(curOutandHidOut[0], curOutandHidOut[1], outVec,
						inVec);
			}
			checkAccuracy(accuracy, tempIn2hidMat, tempHid2outMat);
		}
		in2hidMat = tempIn2hidMat;
		hid2outMat = tempHid2outMat;
	}

	/**
	 * if the current accuracy is better than the max, save the weights
	 * 
	 * @param accuracy
	 * @param tempIn2hidMat
	 * @param tempHid2outMat
	 */
	private void checkAccuracy(float accuracy, double[][] tempIn2hidMat,
			double[][] tempHid2outMat) {
		Tester tester = new Tester(util);
		if (tester.testNoisy(util.dataset.validItems, false, false) >= accuracy) {
			for (int k = 0; k <= as.inputVecSize; k++) {
				for (int j = 0; j < numofHid; j++) {
					tempIn2hidMat[k][j] = in2hidMat[k][j];
				}
			}
			for (int k = 0; k <= numofHid; k++) {
				for (int j = 0; j < as.outputVecSize; j++) {
					tempHid2outMat[k][j] = hid2outMat[k][j];
				}
			}
		}
	}
}

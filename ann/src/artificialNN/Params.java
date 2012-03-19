package artificialNN;

class Params {
	String ds;
	float learnRate;
	int hidUnits;
	float momentum;
	int iter;
	int randomSeed;
	boolean printLearnProcess;
	int validationSize;

	/**
	 * returns params from the main form as an object
	 * 
	 * @param ds
	 * @param numofHidUnits
	 * @param learnRate
	 * @param momentum
	 * @param numofIter
	 * @param randomSeed
	 */
	public Params(String ds, String numofHidUnits, String learnRate,
			String momentum, String numofIter, String randomSeed,
			boolean printLearnProcess, String validationSize) {
		this.ds = new String(ds);
		this.hidUnits = Integer.parseInt(numofHidUnits);
		this.learnRate = Float.parseFloat(learnRate);
		this.momentum = Float.parseFloat(momentum);
		this.iter = Integer.parseInt(numofIter);
		this.randomSeed = Integer.parseInt(randomSeed);
		this.printLearnProcess = printLearnProcess;
		this.validationSize = Integer.parseInt(validationSize);
	}
}

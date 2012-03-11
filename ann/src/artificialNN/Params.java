package artificialNN;

class Params {
	String ds;
	float learnRate;
	int hidUnits;
	float momentum;
	int iter;
	int randomSeed;
	// ************************************************************************************************************************************
	int corruptionPercent;
	int validationPercent;

	public Params(String ds, String numofHidUnits, String learnRate,
			String momentum, String numofIter, String randomSeed) {
		this.ds = new String(ds);
		this.hidUnits = Integer.parseInt(numofHidUnits);
		this.learnRate = Float.parseFloat(learnRate);
		this.momentum = Float.parseFloat(momentum);
		this.iter = Integer.parseInt(numofIter);
		this.randomSeed = Integer.parseInt(randomSeed);
	}
}

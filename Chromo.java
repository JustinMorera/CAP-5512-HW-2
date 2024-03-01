/******************************************************************************
 * A Teaching GA Developed by Hal Stringer & Annie Wu, UCF
 * Version 2, January 18, 2004
 *******************************************************************************/

public class Chromo {
	/*******************************************************************************
	 * INSTANCE VARIABLES *
	 *******************************************************************************/

	public String chromo;
	public double rawFitness;
	public double sclFitness;
	public double proFitness;

	/*******************************************************************************
	 * INSTANCE VARIABLES *
	 *******************************************************************************/

	private static double randnum;

	/*******************************************************************************
	 * CONSTRUCTORS *
	 *******************************************************************************/

	public Chromo() {

		// Set gene values to a randum sequence of 1's and 0's
		char geneBit;
		chromo = "";
		for (int i = 0; i < Parameters.numGenes; i++) {
			for (int j = 0; j < Parameters.geneSize; j++) {
				randnum = Search.r.nextDouble();
				if (randnum > 0.5)
					geneBit = '0';
				else
					geneBit = '1';
				this.chromo = chromo + geneBit;
			}
		}

		this.rawFitness = -1; // Fitness not yet evaluated
		this.sclFitness = -1; // Fitness not yet scaled
		this.proFitness = -1; // Fitness not yet proportionalized
	}

	/*******************************************************************************
	 * MEMBER METHODS *
	 *******************************************************************************/

	// Get Alpha Represenation of a Gene **************************************

	public String getGeneAlpha(int geneID) {
		int start = geneID * Parameters.geneSize;
		int end = (geneID + 1) * Parameters.geneSize;
		String geneAlpha = this.chromo.substring(start, end);
		return (geneAlpha);
	}

	// Get Integer Value of a Gene (Positive or Negative, 2's Compliment) ****

	public int getIntGeneValue(int geneID) {
		String geneAlpha = "";
		int geneValue;
		char geneSign;
		char geneBit;
		geneValue = 0;
		geneAlpha = getGeneAlpha(geneID);
		for (int i = Parameters.geneSize - 1; i >= 1; i--) {
			geneBit = geneAlpha.charAt(i);
			if (geneBit == '1')
				geneValue = geneValue + (int) Math.pow(2.0, Parameters.geneSize - i - 1);
		}
		geneSign = geneAlpha.charAt(0);
		if (geneSign == '1')
			geneValue = geneValue - (int) Math.pow(2.0, Parameters.geneSize - 1);
		return (geneValue);
	}

	// Get Integer Value of a Gene (Positive only) ****************************

	public int getPosIntGeneValue(int geneID) {
		String geneAlpha = "";
		int geneValue;
		char geneBit;
		geneValue = 0;
		geneAlpha = getGeneAlpha(geneID);
		for (int i = Parameters.geneSize - 1; i >= 0; i--) {
			geneBit = geneAlpha.charAt(i);
			if (geneBit == '1')
				geneValue = geneValue + (int) Math.pow(2.0, Parameters.geneSize - i - 1);
		}
		return (geneValue);
	}

	// Mutate a Chromosome Based on Mutation Type *****************************

	public void doMutation() {

		String mutChromo = "";
		char x;

		switch (Parameters.mutationType) {

			case 1: // Replace with new random number

				for (int j = 0; j < (Parameters.geneSize * Parameters.numGenes); j++) {
					x = this.chromo.charAt(j);
					randnum = Search.r.nextDouble();
					if (randnum < Parameters.mutationRate) {
						if (x == '1')
							x = '0';
						else
							x = '1';
					}
					mutChromo = mutChromo + x;
				}
				this.chromo = mutChromo;
				break;

			default:
				System.out.println("ERROR - No mutation method selected");
		}
	}

	/*******************************************************************************
	 * STATIC METHODS *
	 *******************************************************************************/

	// Select a parent for crossover ******************************************

	public static int selectParent() {

		double rWheel = 0;
		int j = 0;
		// int k = 0;

		switch (Parameters.selectType) {

			case 1: // Proportional Selection
				randnum = Search.r.nextDouble();
				for (j = 0; j < Parameters.popSize; j++) {
					rWheel = rWheel + Search.member[j].proFitness;
					if (randnum < rWheel)
						return (j);
				}
				break;

			case 2: // Tournament Selection
				int tournamentSize = Parameters.tournamentSize;

				if (tournamentSize <= 0) {
					System.out.println("Error: Invalid tournament size");
				}

				int[] chosenIndices = new int[tournamentSize];

				// Randomly select individuals for the tournament
				for (int i = 0; i < tournamentSize; i++) {
					int randomIndex = (int) (Search.r.nextDouble() * Parameters.popSize);
					chosenIndices[i] = randomIndex;
				}

				// Find the fittest individual from the chosen ones
				j = chosenIndices[0];
				for (int i = 1; i < tournamentSize; i++) {
					if (Search.member[chosenIndices[i]].rawFitness > Search.member[j].rawFitness) {
						j = chosenIndices[i];
					}
				}

				return j;

			case 3: // Random Selection
				randnum = Search.r.nextDouble();
				j = (int) (randnum * Parameters.popSize);
				return (j);

			default:
				System.out.println("ERROR - No selection method selected");
		}

		return (-1);

	}

	// Produce a new child from two parents **********************************

	public static void mateParents(int pnum1, int pnum2, Chromo parent1, Chromo parent2, Chromo child1, Chromo child2) {

		int xoverPoint1;
		int xoverPoint2;

		switch (Parameters.xoverType) {

			case 1: // Single Point Crossover

				// Select crossover point
				xoverPoint1 = 1 + (int) (Search.r.nextDouble() * (Parameters.numGenes * Parameters.geneSize - 1));

				// Create child chromosome from parental material
				child1.chromo = parent1.chromo.substring(0, xoverPoint1) + parent2.chromo.substring(xoverPoint1);
				child2.chromo = parent2.chromo.substring(0, xoverPoint1) + parent1.chromo.substring(xoverPoint1);
				break;

			case 2: // Two Point Crossover
				// Select crossover point
				xoverPoint1 = 1 + (int) (Search.r.nextDouble() * (Parameters.numGenes * Parameters.geneSize - 1));
				xoverPoint2 = 1 + (int) (Search.r.nextDouble() * (Parameters.numGenes * Parameters.geneSize - 1));

				// Create child chromosome from parental material
				if (xoverPoint2 < xoverPoint1) {
					int temp = xoverPoint1;
					xoverPoint1 = xoverPoint2;
					xoverPoint2 = temp;
				}
				child1.chromo = parent1.chromo.substring(0, xoverPoint1)
						+ parent2.chromo.substring(xoverPoint1, xoverPoint2) + parent1.chromo.substring(xoverPoint2);
				child2.chromo = parent2.chromo.substring(0, xoverPoint1)
						+ parent1.chromo.substring(xoverPoint1, xoverPoint2) + parent2.chromo.substring(xoverPoint2);
				break;

			case 3: // Adaptive Crossover 1: Lower rate per block based on proportion of
					// completeness
				double[] probabilities = new double[Parameters.numGenes];
				double cumulativeProbability = 0;
				double totalProbabilities = 0;

				// Make sure parent1 is most fit
				if (parent1.rawFitness < parent2.rawFitness) {
					Chromo temp = parent1;
					parent1 = parent2;
					parent2 = temp;
				}

				// Modify crossover chance for each bit in block one based on that block's
				// fitness
				for (int i = 0; i < Parameters.numGenes; i++) {
					String block = parent1.chromo.substring(i * Parameters.geneSize,
							i * Parameters.geneSize + Parameters.geneSize);
					int count = 0;
					for (int j = 0; j < Parameters.geneSize; j++) {
						if (block.charAt(j) == '1') {
							count++;
						}
					}
					probabilities[i] = Parameters.xoverRate * (Parameters.xoverRate - count / Parameters.geneSize);
				}

				for (double p : probabilities) {
					totalProbabilities += p;
				}

				double randomNum = Search.r.nextDouble() * totalProbabilities;

				// Choose block proportionately, then choose random bit in that block
				for (int i = 0; i < Parameters.numGenes; i++) {
					cumulativeProbability += probabilities[i];
					if (cumulativeProbability >= randomNum) {
						xoverPoint1 = i * Parameters.numGenes + (int) (Search.r.nextDouble()) * Parameters.geneSize;
						// Then perform 1-point Crossover
						child1.chromo = parent1.chromo.substring(0, xoverPoint1)
								+ parent2.chromo.substring(xoverPoint1);
						child2.chromo = parent2.chromo.substring(0, xoverPoint1)
								+ parent1.chromo.substring(xoverPoint1);
						break;
					}
				}

				// Unused ideas:
				// Adaptive2: lower rate per block per consecutive 1's; 11110011, the first 4
				// 1's will have .5 the chance of being split, and the last 2 1's will have .25
				// the chance
				// Adaptive Mutation: higher chance of mutation for bits in a block based on how
				// complete the block is; 1110111, will have 7x mutation rate of 00000100; do we
				// make 11111111 impossible to mutate?
				break;

			default:
				System.out.println("ERROR - Bad crossover method selected");
		}

		// Set fitness values back to zero
		child1.rawFitness = -1; // Fitness not yet evaluated
		child1.sclFitness = -1; // Fitness not yet scaled
		child1.proFitness = -1; // Fitness not yet proportionalized
		child2.rawFitness = -1; // Fitness not yet evaluated
		child2.sclFitness = -1; // Fitness not yet scaled
		child2.proFitness = -1; // Fitness not yet proportionalized
	}

	// Produce a new child from a single parent ******************************

	public static void mateParents(int pnum, Chromo parent, Chromo child) {

		// Create child chromosome from parental material
		child.chromo = parent.chromo;

		// Set fitness values back to zero
		child.rawFitness = -1; // Fitness not yet evaluated
		child.sclFitness = -1; // Fitness not yet scaled
		child.proFitness = -1; // Fitness not yet proportionalized
	}

	// Copy one chromosome to another ***************************************

	public static void copyB2A(Chromo targetA, Chromo sourceB) {

		targetA.chromo = sourceB.chromo;

		targetA.rawFitness = sourceB.rawFitness;
		targetA.sclFitness = sourceB.sclFitness;
		targetA.proFitness = sourceB.proFitness;
		return;
	}

} // End of Chromo.java ******************************************************

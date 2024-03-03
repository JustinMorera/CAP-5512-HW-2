/******************************************************************************
*  A Teaching GA					  Developed by Hal Stringer & Annie Wu, UCF
*  Version 2, January 18, 2004
*******************************************************************************/

import java.io.*;

public class RoyalRoad extends FitnessFunction{

/*******************************************************************************
*                            INSTANCE VARIABLES                                *
*******************************************************************************/


/*******************************************************************************
*                            STATIC VARIABLES                                  *
*******************************************************************************/


/*******************************************************************************
*                              CONSTRUCTORS                                    *
*******************************************************************************/

	public RoyalRoad(){
		name = "Royal Road Problem";
	}

/*******************************************************************************
*                                MEMBER METHODS                                *
*******************************************************************************/

//  COMPUTE A CHROMOSOME'S RAW FITNESS *************************************

	public void doRawFitness(Chromo X){
		X.rawFitness = 0;
		int n = (int)(Math.log10(Parameters.numGenes) / Math.log10(2)) - 1; // n is order of building blocks
		int m; // m helps keep track of which order is being evaluated

		switch (Parameters.variant)
		{
			case "R1": // Royal Road 1 simply counts every 1st-order building block as a fitness-boosting schema
				// Checks each gene for completeness, then adds the reward if it is complete
				for (int z = 0; z < Parameters.numGenes; z++){
					if (!(X.chromo.substring(z * Parameters.geneSize, z * Parameters.geneSize + Parameters.geneSize).contains("0")))
						X.rawFitness += Parameters.reward;
				}
				break;
			case "R2": // Royal Road 2 counts every building block as a fitness-boosting schema but increasing the boost exponentially based on its order
				// Checks each gene for completeness, then adds the reward if it is complete
				for (int z = 0; z < Parameters.numGenes; z++){
					for (int i = n; i >= 0; i--) // Also checks each order the gene belongs in, so every 2^i genes count for multiple levels of fitness
					{
						m = (int)Math.pow(2, i);
						if ((1 + z) % m == 0)
							if (!(X.chromo.substring((z - (m - 1)) * Parameters.geneSize, z * Parameters.geneSize + Parameters.geneSize).contains("0")))
								X.rawFitness += Parameters.reward * m;
					}
				}
				break;
            case "R2Fat": // Royal Road 2 counts every building block as a fitness-boosting schema but increasing the boost exponentially based on its order
				// Checks each gene for completeness, then adds the reward if it is complete
				for (int z = 0; z < Parameters.numGenes; z++){
					for (int i = n; i >= 0; i--) // Also checks each order the gene belongs in, so every 2^i genes count for multiple levels of fitness
					{
						m = (int)Math.pow(2, i);
						if ((1 + z) % m == 0)
							if (!(X.chromo.substring((z - (m - 1)) * Parameters.geneSize, z * Parameters.geneSize + Parameters.geneSize).contains("0")))
								X.rawFitness += Parameters.reward * Math.pow(m, 2);
					}
				}
				break;
            case "R2Lean": // Royal Road 2 counts every building block as a fitness-boosting schema but increasing the boost exponentially based on its order
				// Checks each gene for completeness, then adds the reward if it is complete
				for (int z = 0; z < Parameters.numGenes; z++){
					for (int i = n; i >= 0; i--) // Also checks each order the gene belongs in, so every 2^i genes count for multiple levels of fitness
					{
						m = (int)Math.pow(2, i);
						if ((1 + z) % m == 0)
							if (!(X.chromo.substring((z - (m - 1)) * Parameters.geneSize, z * Parameters.geneSize + Parameters.geneSize).contains("0")))
								X.rawFitness += Parameters.reward / m;
					}
				}
				break;
			// case "R1F":
			// 	for (int z = 0; z < Parameters.numGenes; z++){
			// 		if (!(X.chromo.substring(z * Parameters.geneSize, z * Parameters.geneSize + Parameters.geneSize).contains("0")))
			// 			X.rawFitness += Parameters.reward;
			// 	}
			// 	break;
			case "R2F": // Royal Road 2 Flat counts every building block as a fitness-boosting schema but increasing the boost linearly based on its order
				for (int z = 0; z < Parameters.numGenes; z++){
					for (int i = n; i >= 0; i--)
					{
						m = (int)Math.pow(2, i);
						if ((1 + z) % m == 0)
							if (!(X.chromo.substring((z - (m - 1)) * Parameters.geneSize, z * Parameters.geneSize + Parameters.geneSize).contains("0")))
								X.rawFitness += Parameters.reward; // Each block adds only the reward amount for each order it completes
					}
				}
				break;
			default:
				System.out.println("Invalid Variant Type");
		}
	}

//  PRINT OUT AN INDIVIDUAL GENE TO THE SUMMARY FILE *********************************

	public void doPrintGenes(Chromo X, FileWriter output) throws java.io.IOException{

		for (int i=0; i<Parameters.numGenes; i++){
			Hwrite.right(X.getGeneAlpha(i),11,output);
		}
		output.write("   RawFitness");
		output.write("\n        ");
		for (int i=0; i<Parameters.numGenes; i++){
			Hwrite.right(X.getPosIntGeneValue(i),11,output);
		}
		Hwrite.right((int) X.rawFitness,13,output);
		output.write("\n\n");
		return;
	}

/*******************************************************************************
*                             STATIC METHODS                                   *
*******************************************************************************/

}   // End of OneMax.java ******************************************************

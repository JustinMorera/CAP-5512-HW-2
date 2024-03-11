import pandas as pd
import matplotlib.pyplot as plt

variants = ["R1", "R2", "R2F"]
numGenes = [4, 8, 16]
geneSizes = [4, 8, 16]

doNormalize = True

# Sorted optimums for each variant and numGenes+geneSize combination. Inner-most dictionary is numGenes: optimumFitness. 
optimums = { "R1": {4: 32, 8: 64, 16: 128},
 "R2": {4: 64, 8: 192, 16: 512},
 "R2F": {4: 6, 8: 14, 16: 30} 
}

def normalize(data, variant, numGene, geneSize):
  """Because every numGene+geneSize combination has a different optimal fitness value, we normalize the data to be able to compare them.

  Args:
      data (pd.DataFrame): Dataframe with columns 'Generation', 'AverageFitness', 'StdDev'.
      variant (string): Royal Road Variant.
      numGene (int): number of genes
      geneSize (int): number of bits in each gene

  Returns:
      pd.DataFrame: Normalized dataframe with columns 'Generation', 'AverageFitness', 'StdDev'.
  """
  data['AverageFitness'] = data['AverageFitness'] / (optimums[variant][numGene])
  data['StdDev'] = data['StdDev'] / (optimums[variant][numGene])
  return data

def export_graph_variant_based(variant, numGenes, geneSizes):
  """Makes a graph of the average fitness of a genetic algorithm over generations, using filename generated from input params.

  Args:
      variant (string): Royal Road Variant.
      numGenes (int[]): number of genes
      geneSizes (int[]): number of bits in each gene
  """
  plt.figure(figsize=(10, 6))
  for numGene in numGenes:
    for geneSize in geneSizes:
      if numGene * geneSize == 64:
        filename = f"mike_outputs/graph_data_rr_variant-{variant}_numGenes-{numGene}_geneSize-{geneSize}.csv"
        data = pd.read_csv(filename)
        if (doNormalize):
          data = normalize(data, variant, numGene, geneSize)

        plt.errorbar(data['Generation'], data['AverageFitness'], yerr=data['StdDev'], fmt='-o', ecolor='gray', alpha=0.5, capsize=5, label=f'Average Fitness with Standard Deviation; {numGene} genes, {geneSize} bits per gene')
        plt.plot(data['Generation'], data['AverageFitness'])
        

  plt.xlabel('Generation')
  plt.ylabel('Fitness')
  tit = f'Variant {variant}; Average Average Fitness Vs. Generations'
  figTitle = f"mike_graphs/AvgAvgFitnessVsGenerations_variant-{variant}.png"
  if (doNormalize):
    tit = f'Variant {variant}; Optimum-Normalized Average Average Fitness Vs. Generations'
    figTitle = f"mike_graphs/norm_AvgAvgFitnessVsGenerations_variant-{variant}.png"
  plt.title(tit)
  plt.legend()
  plt.grid(True)

  plt.savefig(figTitle)

for variant in variants:
  export_graph_variant_based(variant, numGenes, geneSizes)


def export_graph_numGene_based(numGene, variants, geneSizes):
  """Makes a graph of the average fitness of a genetic algorithm over generations, using filename generated from input params.

  Args:
      variant (string): Royal Road Variant.
      numGenes (int[]): number of genes
      geneSizes (int[]): number of bits in each gene
  """
  plt.figure(figsize=(10, 6))
  for variant in variants:
    for geneSize in geneSizes:
      if numGene * geneSize == 64:
        filename = f"mike_outputs/graph_data_rr_variant-{variant}_numGenes-{numGene}_geneSize-{geneSize}.csv"
        data = pd.read_csv(filename)
        if (doNormalize):
          data = normalize(data, variant, numGene, geneSize)

        plt.errorbar(data['Generation'], data['AverageFitness'], yerr=data['StdDev'], fmt='-o', ecolor='gray', alpha=0.5, capsize=5, label=f'Average Fitness with Standard Deviation; Variant {variant}, {geneSize} bits per gene')
        plt.plot(data['Generation'], data['AverageFitness'])
        

  plt.xlabel('Generation')
  plt.ylabel('Fitness')
  figTitle = f"mike_graphs/AvgAvgFitnessVsGenerations_numGenes-{numGene}.png"
  tit = f'{numGene} Genes; Average Average Fitness Vs. Generations'
  if (doNormalize):
    figTitle = f"mike_graphs/norm_AvgAvgFitnessVsGenerations_numGenes-{numGene}.png"
    tit = f'{numGene} Genes; Optimum-Normalized Average Average Fitness Vs. Generations'
  plt.title(tit)
  plt.legend()
  plt.grid(True)

  plt.savefig(figTitle)

for numGene in numGenes:
        export_graph_numGene_based(numGene, variants, geneSizes)
        
        
def export_graph_geneSize_based(geneSize, variants, numGenes):
  """Makes a graph of the average fitness of a genetic algorithm over generations, using filename generated from input params.

  Args:
      variant (string): Royal Road Variant.
      numGenes (int[]): number of genes
      geneSizes (int[]): number of bits in each gene
  """
  plt.figure(figsize=(10, 6))
  for numGene in numGenes:
    for variant in variants:
      if numGene * geneSize == 64:
        filename = f"mike_outputs/graph_data_rr_variant-{variant}_numGenes-{numGene}_geneSize-{geneSize}.csv"
        data = pd.read_csv(filename)
        if (doNormalize):
          data = normalize(data, variant, numGene, geneSize)

        plt.errorbar(data['Generation'], data['AverageFitness'], yerr=data['StdDev'], fmt='-o', ecolor='gray', alpha=0.5, capsize=5, label=f'Average Fitness with Standard Deviation; Variant {variant}, {numGene} genes')
        plt.plot(data['Generation'], data['AverageFitness'])
        

  plt.xlabel('Generation')
  plt.ylabel('Fitness')
  figTitle = f"mike_graphs/AvgAvgFitnessVsGenerations_geneSize-{geneSize}.png"
  tit = f'geneSize {geneSize}; Average Average Fitness Vs. Generations'
  if (doNormalize):
    figTitle = f"mike_graphs/norm_AvgAvgFitnessVsGenerations_geneSize-{geneSize}.png"
    tit = f'geneSize {geneSize}; Optimum-Normalized Average Average Fitness Vs. Generations'
  plt.title(tit)
  plt.legend()
  plt.grid(True)
  plt.savefig(figTitle)

for geneSize in geneSizes:
        export_graph_geneSize_based(geneSize, variants, numGenes)
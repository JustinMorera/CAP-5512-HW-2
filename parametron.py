# default, 3 test values
popsizes = [100, 50, 200, 400] # 2
selectionmethods = [1, 1, 2, 3] # 5
crossoverrates = [0.8, 0.4, 1.0, 0.2] # 4
mutrates = [0.005, 0.001, 0.01, 0.0005]# 3

variants = ["R1", "R2", "R2F"]
numGenes = [4, 8, 16]
geneSizes = [4, 8, 16]

filenames = []


def export(filename, variant, numGenes, geneSize):
  with open(f"mike_suite/{filename}", "w") as text_file:
    print(f"""Experiment ID                :royalroad
Problem Type                 :RR
Variant (9)                  :{variant}
Reward (10)                  :{1 if variant == "R2F" else 8}
Data Input File Name         :NA
Number of Runs               :100
Generations per Run          :500
Population Size              :128
Selection Method (1)         :2
Tournament Size              :5
Fitness Scaling Type (2)     :0
Crossover Type (3)           :1
Crossover Rate (4)           :0.8
Mutation Type (5)            :1
Mutation Rate (6)            :0.005
Random Number Seed           :75982
Number of Genes/Points (7)   :{numGenes}
Size of Genes (8)            :{geneSize}

Notes:

1)  Selection Type Codes    1 = Proportional Selection
							2 = Tournament Selection
							3 = Random Selection

2)  Fitness Scaling Type    0 = Scale for Maximization (no change to raw fitness)
							1 = Scale for Minimization (reciprocal of raw fitness)
							2 = Rank for Maximization
							3 = Rank for Minimization

3)  Crossover Type Codes    1 = Single Point Crossover
							2 = Two Point Crossover
							3 = Adaptive Crossover 1: lower rate per block based on proportion of completeness
							4 = Adaptive Crossover 2: lower rate per block per consecutive completeness

4)  Crossover Rates from 0 to 1, Use "0" to turn off crossover

5)  Mutation Type Codes     1 = Flip Bit

6)  Mutation Rates from 0 to 1, Use "0" to turn off mutation

7)	Represents number of genes in each chromosome.

8)	Determines number of bits in each gene.  Number of Genes times Size
	gives the number of bits in each chromosome.

9) Variants		R1 = Royal Road 1
				R2 = Royal Road 2
				R1F = Royal Road 1 Flat* *Possibly a defunct function?
				R2F = Royal Road 2 Flat

10) Reward determines value of each block""", file=text_file)
    
for variant in variants:
  for numGene in numGenes:
    for geneSize in geneSizes:
      if numGene * geneSize == 64:
        filename = f"rr_variant-{variant}_numGenes-{numGene}_geneSize-{geneSize}.params"
        filenames.append(filename)
        export(filename, variant, numGene, geneSize)
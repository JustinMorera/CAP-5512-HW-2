import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv('graph_data.csv')

plt.figure(figsize=(10, 6))
plt.plot(data['Generation'], data['AverageFitness'], label='Average Fitness')
plt.errorbar(data['Generation'], data['AverageFitness'], yerr=data['StdDev'], fmt='-o', ecolor='gray', alpha=0.5, capsize=5, label='Standard Deviation')

plt.xlabel('Generation')
plt.ylabel('Fitness')
plt.title('Average Average Over Generations')
plt.legend()
plt.grid(True)

plt.show()


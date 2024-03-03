import pandas as pd
import matplotlib.pyplot as plt

def plot_selected_building_blocks(orders_blocks_dict):
    plt.figure(figsize=(12, 8))

    for order, blocks in orders_blocks_dict.items():
        data = pd.read_csv(f'buildingBlocks_order_{order}.csv')

        for block in blocks:
            block_column_name = f'Block_{block}'
            if block_column_name in data.columns:
                plt.plot(data['Generation'], data[block_column_name], label=f'Order {order}, Block {block}')

    plt.xlabel('Generation')
    plt.ylabel('Density')
    plt.title('Evolution of schemas')
    plt.legend()
    plt.grid(True)
    plt.savefig(f"EvolutionofSchemas.png")

# This version of the function will split the graph into two subplots.
def plot_selected_building_blocks_(orders_blocks_dict, title, filename):
    fig, (ax1, ax2) = plt.subplots(2, 1, sharex=True, figsize=(12, 8), gridspec_kw={'height_ratios': [2, 1]})  # Create two subplots sharing the same x axis

    for order, blocks in orders_blocks_dict.items():
        data = pd.read_csv(f'buildingBlocks_order_{order}.csv')

        for block in blocks:
            block_column_name = f'Block_{block}'
            if block_column_name in data.columns:
                plt.plot()
                # Plot the lower range data on the first subplot
                ax1.plot(data['Generation'], data[block_column_name], label=f'Order {order}, Block {block}')
                ax1.set_ylim(90, max(data[block_column_name]+20))  # Set the limits of y-axis from 100 to max(y)

                # Plot the upper range data on the second subplot
                ax2.plot(data['Generation'], data[block_column_name], label=f'Order {order}, Block {block}')
                ax2.set_ylim(0, 20)  # Set the limits of y-axis from 0 to 20

    # Hide the spines between ax and ax2
    ax1.spines['bottom'].set_visible(False)
    ax2.spines['top'].set_visible(False)
    ax1.xaxis.tick_top()
    ax1.tick_params(labeltop=False)  # don't put tick labels at the top
    ax2.xaxis.tick_bottom()
    
    plt.xlabel('Generation')
    ax1.set_ylabel('Density')
    ax1.set_title(f'Evolution of schemas in {title}')
    ax1.legend()
    ax1.grid(True)
    ax2.grid(True)
    fig.savefig(f"EvolutionofSchemas_{filename}.png")

blocks = [{0: [0, 1], 1: [0], 2: range(0)}, 
          {0: [2, 3], 1: [1], 2: range(0)}, 
          {0: [4, 5], 1: [2], 2: range(0)},
          {0: [6, 7], 1: [3], 2: range(0)},
          {0: range(0), 1: [0, 1], 2: [0]},
          {0: range(0), 1: [2, 3], 2: [1]}]
titles = ['Block 0 of Order 1', 'Block 1 of Order 1', 'Block 2 of Order 1', 'Block 3 of Order 1', 'Block 0 of Order 2', 'Block 1 of Order 2']
filenames = ['block_0', 'block_1', 'block_2', 'block_3', 'block_0_order_2', 'block_1_order_2']
for blk, title, fname in zip(blocks, titles, filenames):
    plot_selected_building_blocks_(blk, title, fname)


"""
plot_selected_building_blocks({order: [blocks], order: [blocks], more})
"""


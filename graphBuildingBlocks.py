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
    plt.show()

plot_selected_building_blocks({0: [1], 1: [1], 2: [1]})

"""
plot_selected_building_blocks({order: [blocks], order: [blocks], more})
"""


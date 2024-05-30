import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

# Load the data from the CSV file
data = pd.read_csv('../../test_results.csv')

# Plot the data
fig, ax1 = plt.subplots(figsize=(10, 6))

x = data['Input Value']
# ax1.plot(x, np.exp(x), label='Computed Exp')

ax1.plot(x, data['Computed Exp'], label='Computed Exp')
ax1.plot(x, data['Actual Exp'], label='Actual Exp', linestyle='--')
ax1.set_xlabel('Input Value')
ax1.set_ylabel('Exponential Value')
ax1.set_ylim(0.5, 1.1)

ax2 = ax1.twinx()
ax2.plot(data['Input Value'], data['Relative Error (%)'], label='Relative Error (%)', color='green', linestyle=':')
ax2.set_ylabel('Relative Error (%)')
ax2.set_ylim(0, 3)


plt.title('Comparison of Computed and Actual Values')
lines, labels = ax1.get_legend_handles_labels()
lines2, labels2 = ax2.get_legend_handles_labels()
ax2.legend(lines + lines2, labels + labels2, loc='upper right')

# plt.grid(True)
# plt.show()
plt.savefig('../../exp_plot.png')

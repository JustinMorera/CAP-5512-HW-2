build:
	javac *.java

clean_csv:
	rm -f graph_data.csv buildingBlocks_order_*.csv

run: clean_csv build
	java Search rr.params

graph: run
	python3 graph.py
	python3 graphBuildingBlocks.py

clean:
	rm -f *.class


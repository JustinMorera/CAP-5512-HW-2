build:
	javac *.java

clean_csv:
	rm -f graph_data.csv buildingBlocks_order_*.csv

run: clean_csv build
	java Search rr.params

graph: run
	python3 graph.py
	python3 graphBuildingBlocks.py

# run_mike:
# 		for file in mike_suite/*; do \
# 				echo $$file; \
# 				java Search $$file > output.txt; \
# 				mv output.txt mike_outputs/$$(basename $$file .params).txt; \
# 				mv buildingBlocks_order_0.csv mike_outputs/buildingBlocks_order_0_$$(basename $$file .params).csv; \
# 				mv buildingBlocks_order_1.csv mike_outputs/buildingBlocks_order_1_$$(basename $$file .params).csv; \
# 				mv buildingBlocks_order_2.csv mike_outputs/buildingBlocks_order_2_$$(basename $$file .params).csv; \
# 				mv buildingBlocks_order_3.csv mike_outputs/buildingBlocks_order_3_$$(basename $$file .params).csv; \
# 				mv graph_data.csv mike_outputs/graph_data_$$(basename $$file .params).csv; \
# 				mv royalroad_summary.txt mike_outputs/royalroad_summary_$$(basename $$file .params).txt; \
# 		done

run_mike:
		for file in mike_suite/*; do \
				echo $$file; \
				java Search $$file > output.txt; \
				while lsof -t output.txt > /dev/null; do sleep 1; done; \
				mv output.txt mike_outputs/$$(basename $$file .params).txt; \
				while lsof -t buildingBlocks_order_0.csv > /dev/null; do sleep 1; done; \
				mv buildingBlocks_order_0.csv mike_outputs/buildingBlocks_order_0_$$(basename $$file .params).csv; \
				while lsof -t buildingBlocks_order_1.csv > /dev/null; do sleep 1; done; \
				mv buildingBlocks_order_1.csv mike_outputs/buildingBlocks_order_1_$$(basename $$file .params).csv; \
				while lsof -t buildingBlocks_order_2.csv > /dev/null; do sleep 1; done; \
				mv buildingBlocks_order_2.csv mike_outputs/buildingBlocks_order_2_$$(basename $$file .params).csv; \
				while lsof -t buildingBlocks_order_3.csv > /dev/null; do sleep 1; done; \
				mv buildingBlocks_order_3.csv mike_outputs/buildingBlocks_order_3_$$(basename $$file .params).csv; \
				while lsof -t graph_data.csv > /dev/null; do sleep 1; done; \
				mv graph_data.csv mike_outputs/graph_data_$$(basename $$file .params).csv; \
				while lsof -t royalroad_summary.txt > /dev/null; do sleep 1; done; \
				mv royalroad_summary.txt mike_outputs/royalroad_summary_$$(basename $$file .params).txt; \
		done

clean:
	rm -f *.class


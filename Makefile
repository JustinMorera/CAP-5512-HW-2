build:
	javac *.java

run: build
	java Search rr.params

clean:
	rm -f *.class
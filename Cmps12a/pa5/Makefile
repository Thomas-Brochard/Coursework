#Makefile
#Thomas Brochard
#pa5
#Makefile for pa5 Queens.java program
Queens: Queens.class
	echo Main-class: Queens > Manifest
	jar cvfm Queens Manifest Queens.class
	rm Manifest
	chmod u+x Queens

Queens.class: Queens.java
	javac -Xlint Queens.java

clean:
	rm -f Queens.class Queens

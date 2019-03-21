JFLAGS = -g
JC = javac -cp src
JVM= java -cp src Main
File= 
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	src/Main.java \
	src/UserThread.java \
	src/PrintJobThread.java 

MAIN = Main

default: classes run

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) ./src/*.class
	

all: compile run

sources = ./src/main/Main.java ./src/input/FileHandler.java ./src/convert/Ascii.java ./src/convert/Braille.java ./src/input/ArgHandler.java

compile: $(sources) 
	mkdir -p target/braille/
	javac -cp src/ $(sources) -d target

run:
	java -cp target braille/main/Main $(args) 

install: compile
	mv ./target/braille /usr/local/share/
	cp ./bin/braille /usr/local/bin/

uninstall:
	rm -rf /usr/local/share/braille/
	rm /usr/local/bin/braille

clean:
	rm -rf ./target/

all: compile run

compile: ./src/main/Main.java ./src/input/FileHandler.java ./src/convert/ASCII.java ./src/convert/Braille.java ./src/utils/Constants.java 
	mkdir -p target/braille/
	javac -cp "./src/" "./src/input/FileHandler.java" "./src/main/Main.java" "./src/convert/ASCII.java" "./src/convert/Braille.java" "./src/utils/Constants.java" -d target

run:
	rm -f ./output.txt
	java -cp target braille/main/Main >> ./output.txt
	cat ./output.txt

clean:
	rm -rf ./.target/
	mv ./target/ ./.target/

deploy:
	rm -f project.zip
	zip project.zip -r "./src/" makefile 

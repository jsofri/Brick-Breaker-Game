# 205433329
# sofriyere
compile: bin
	find src -name "*.java" > sources.txt
	javac -cp biuoop-1.4.jar:. @sources.txt -d bin

run:
	java -cp biuoop-1.4.jar:bin:resources brickbreaker/basics/Ass7Game

jar:
	jar cfm ass7game.jar Mainfest.mf -C bin . -C resources .

bin:
	mkdir bin
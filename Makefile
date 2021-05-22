default: build

.PHONY: clean
clean:
	mvn clean

.PHONY: build
build:
	mvn package

.PHONY: run
run:
	java -cp ./target/miez-attack-1.0.0.jar com.irgndsondepp.clone.Game

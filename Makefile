.PHONY: all clean test build

all: assemble

test:
	@echo "\nRunning unit tests\n"
	@./gradlew test

build:
	@echo "\nBuilding application"
	@./gradlew build -Dquarkus.package.type=uber-jar

run-local: build
	@echo "\nRunning without containerization"
	@java -jar build/quarkus-app/quarkus-run.jar

assemble: clean
	@echo "\nCreating Docker container"
	@docker build --tag quarkus/fib .
	@printf "\nContainer created. Run %bmake install%b to install fib\n" "\e[1;33m" "\e[0m"

install:
	@echo "\nCreating executable"
	@chmod +x scripts/install.sh
	@$(shell scripts/install.sh)
	@echo "\nGiven execution permission to executable file"
	@chmod +x fib
	@printf "\nAll setup. %bfib%b is ready\n %b./fib%b to start the server\n" "\e[1;32m" "\e[0m" "\e[1;32m" "\e[0m"

clean:
	@echo "\nRemove fib executable"
	-@rm fib 2>/dev/null || echo "\nExecutable file fib not found to remove"
	@echo "\nRemove fib image"
	-@docker rmi quarkus/fib 2>/dev/null || echo "\nDocker image fib not found to remove\n"

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

start-k8s:
	@echo "\nStarting k8s cluster"
	-@minikube start 2>/dev/null || echo "\nMinikube not started\n"

stop-k8s:
	@echo "\nStopping k8s cluster"
	-@minikube stop 2>/dev/null || echo "\nMinikube not stopped\n"

deploy-k8s:
	@echo "\nDeploy fib application"
	-@kubectl apply -f fib.yaml 2>/dev/null || echo "\nDeployment of fib not started\n"
	@echo "\nDeploy fib autoscaler"
	-@kubectl apply -f fib_hpa.yaml 2>/dev/null || echo "\nDeployment of fib autoscaler not started\n"

clean-k8s:
	@echo "\nDeleting fib application"
	-@kubectl delete deployment/fib-deployment 2>/dev/null || echo "\nfib not deleted\n"
	@echo "\nDeleting fib autoscaler"
	-@kubectl delete horizontalpodautoscaler/fib-hpa 2>/dev/null || echo "\nfib-hpa autoscaler not deleted\n"

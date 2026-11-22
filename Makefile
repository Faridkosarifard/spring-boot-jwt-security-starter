.PHONY: test verify package run

test:
	./mvnw -B test

verify:
	./mvnw -B verify

package:
	./mvnw -B -DskipTests package

run:
	./mvnw -B spring-boot:run

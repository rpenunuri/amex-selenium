FROM maven:3.9.6-eclipse-temurin-17-alpine

# Set the working directory
WORKDIR /usr/src/app

# Copy the pom.xml file and download the dependencies. This will allow Docker to cache
# the Maven dependencies and speed up builds.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the code and run the tests.
COPY . .

RUN chmod +x ./scripts/entry-point.sh

# Run the entrypoint script.
ENTRYPOINT ["./scripts/entry-point.sh"]
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy source
COPY . .

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the app
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/gold-silver-tracker-1.0.0.jar"]

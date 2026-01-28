# Use Java 17
FROM eclipse-temurin:17-jdk-jammy

# App directory
WORKDIR /app

# Copy everything
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/gold-silver-tracker-1.0.0.jar"]

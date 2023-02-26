# Base image
FROM openjdk:17-alpine

# Set working directory
WORKDIR /app

# Copy application JAR file
COPY target/defineXFinalCase-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port
EXPOSE 8086

# Start application
CMD ["java", "-jar", "app.jar"]

# Multi-stage build for optimized image size
# Stage 1: Build Stage (Maven Build)
FROM --platform=linux/amd64 maven:3.9-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Runtime stage
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app
# ສ້າງ logs directory ກ່ອນ (ໃນຖານະ root)
RUN mkdir -p /app/logs


# Copy JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t api-gateway:1.0.0 .
# docker run -d --name api-gateway --network app-network -p 8080:8080 api-gateway:1.0.0

#docker build --platform linux/amd64  -t 172.16.4.62:5000/customer/api-gateway:1.0.0
#docker push 172.16.4.62:5000/customer/api-gateway:1.0.0


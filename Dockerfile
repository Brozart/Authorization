# Start with a base image containing Java runtime
FROM openjdk:11-jre-slim

LABEL maintainer="info.brozartapps@gmail.com"

# Install/ update curl
RUN apt-get update \
        && apt-get upgrade -y  --no-install-recommends \
        && apt-get install -y --no-install-recommends curl

# Define standard health check end point
HEALTHCHECK --interval=5s --timeout=3s --retries=20 CMD curl -A "Health check Docker" -f http://localhost:8080/authorization/actuator/health || exit 1

# Expose port
EXPOSE 8080

# Add application
ADD target/authorization-*.jar Authorization.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "/Authorization.jar"]

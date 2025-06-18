FROM --platform=$TARGETPLATFORM gradle:jdk23-alpine AS builder

WORKDIR /app

COPY . .

RUN gradle build -x test --no-daemon


FROM --platform=$TARGETPLATFORM eclipse-temurin:23-jre-alpine AS runtime
WORKDIR /app


COPY --from=builder /app/build/libs/bikehub.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]

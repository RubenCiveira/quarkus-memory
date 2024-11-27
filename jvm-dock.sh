./mvnw clean package -DskipTests 
docker build -f src/main/docker/Dockerfile.jvm --no-cache -t quarkus/code-with-quarkus-jvm .
docker run -i --rm -p 8090:8090 quarkus/code-with-quarkus-jvm

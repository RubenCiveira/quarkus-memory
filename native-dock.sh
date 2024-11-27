# ./mvnw clean package -Dnative -DskipTests -Dquarkus.native.container-runtime=docker -Dquarkus.native.container-build=true
# docker build -f src/main/docker/Dockerfile.native-micro -t quarkus/code-with-quarkus-native-micro .
docker build --no-cache -f src/main/docker/Dockerfile.native-micro.multistage -t quarkus/code-with-quarkus-native-micro .
docker run -i --rm -p 8090:8090 quarkus/code-with-quarkus-native-micro

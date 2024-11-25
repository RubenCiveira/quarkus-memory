# ./mvnw clean package -Dnative -DskipTests -Dquarkus.native.container-runtime=docker -Dquarkus.native.container-build=true
# docker build -f src/main/docker/Dockerfile.native-micro -t quarkus/code-with-quarkus-native-micro .

docker build -f src/main/docker/Dockerfile.native-micro.multistage --no-cache -t quarkus/code-with-quarkus-native-micro .
# docker run -i --rm -p 8080:8080 quarkus/code-with-quarkus-native-micro

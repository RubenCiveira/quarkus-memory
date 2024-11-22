# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Compile and deploy on local docker witha a jvm


```shell script
./jvm-dock.sh
```

it content is:

```shell script
./mvnw clean package -DskipTests 
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/code-with-quarkus-jvm .
docker run -i --rm -p 8080:8080 quarkus/code-with-quarkus-jvm
```

## Compile and deploy on local docker with as native


```shell script
./native-dock.sh
```

it content is:

```shell script
./mvnw clean package -Dnative -DskipTests -Dquarkus.native.container-runtime=docker -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native-micro -t quarkus/code-with-quarkus-native-micro .
docker run -i --rm -p 8080:8080 quarkus/code-with-quarkus-native-micro
```


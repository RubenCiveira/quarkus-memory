# code-with-quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


Codigo base 1 endpoint

|  Distribution  |     RAM    |
| -------------- | ------------- |
| JVM ON DOCKER  |    99Mb    |
| NATIVE ON DOCKER  | 12Mb  |


Codigo base 1 endpoint con prometheus

|  Distribution  |     RAM    |
| -------------- | ------------- |
| JVM ON DOCKER  |    168Mb    |
| NATIVE ON DOCKER  | 21Mb  |

Quitamos prometheus, y añadimos:
 - open telemetry
 - security sobre jwt
 - bucket4j para limites
 - using of yaml config, and split configuring 
 
|  Distribution  |     RAM    |
| -------------- | ------------- |
| JVM ON DOCKER  |    126Mb    |
| NATIVE ON DOCKER  | 22Mb  |

Añadimos:
 - datasource postgresql y lectura de registros de la tabla
Mantenemos:
 - open telemetry
 - security sobre jwt
 - bucket4j para limites
 - using of yaml config, and split configuring 
 
|  Distribution  |     RAM    |
| -------------- | ------------- |
| JVM ON DOCKER  |    126Mb    |
| NATIVE ON DOCKER INICIO  | 20Mb  |
| NATIVE ON DOCKER TRAS QUERIES  | 60Mb  |



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


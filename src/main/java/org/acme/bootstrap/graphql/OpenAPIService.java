package org.acme.bootstrap.graphql;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.parser.OpenAPIV3Parser;
import lombok.Getter;

@Getter
public class OpenAPIService {
  private final OpenAPI openAPI;
  private final String serverUrl;

  public OpenAPIService(InputStream stream, String serverUrl) {
    try (Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
      String yaml = scanner.useDelimiter("\\A").next();
      this.serverUrl = serverUrl;
      this.openAPI = new OpenAPIV3Parser().readContents(yaml).getOpenAPI();
    }
  }

  public Optional<ExecutionNode> buildExecutionTree(String rootEndpoint,
      Map<String, String> selector) {
    Map<String, Map<String, ExecutionNode>> nodes = new HashMap<>();
    // Recorrer los endpoints
    openAPI.getPaths().forEach((path, pathItem) -> {
      for (PathItem.HttpMethod method : pathItem.readOperationsMap().keySet()) {
        if (method == PathItem.HttpMethod.GET) {
          Operation operation = pathItem.readOperationsMap().get(method);
          boolean hasPath = 
              null != operation.getParameters() ? 
              operation.getParameters().stream().noneMatch(pr -> pr.getIn().equals("path")) : true;
          ExecutionNode node = new ExecutionNode(serverUrl, path, method.name(), hasPath);
          // Extraer parámetros
          if (operation.getParameters() != null) {
            operation.getParameters()
                .forEach(param -> node.setParameter(param.getName(),
                    ExecutionParam.builder().name(param.getName())
                        .required(Boolean.TRUE.equals(param.getRequired())).in(param.getIn())
                        .build()));
          }
          operation.getTags().forEach(tag -> {
            if (!nodes.containsKey(tag)) {
              nodes.put(tag, new HashMap<>());
            }
            nodes.get(tag).put(path, node);
          });
        }
      }
    });
    // Construir relaciones
    // for (ExecutionNode node : nodes.values()) {
    // for (Map.Entry<String, ExecutionNode> entry : nodes.entrySet()) {
    // if (entry.getKey().startsWith(node.getEndpoint())
    // && !entry.getKey().equals(node.getEndpoint())) {
    // node.addChild(entry.getValue());
    // }
    // }
    // }
    return nodes.get(rootEndpoint).values().stream().filter(node -> hasPath(selector, node))
        .sorted(this::comparePathParams).findFirst();
  }

  private boolean hasPath(Map<String, String> params, ExecutionNode node) {
    boolean hasAllPassed = node.getParameters().keySet().containsAll(params.keySet());
    boolean hasAllRequired = params.keySet().containsAll(node.getParameters().values().stream()
        .filter(ExecutionParam::isRequired).map(ExecutionParam::getName).toList());
    return hasAllPassed && hasAllRequired;
  }

  private int comparePathParams(ExecutionNode one, ExecutionNode other) {
    return countPathParams(other) - countPathParams(one);
  }

  private int countPathParams(ExecutionNode one) {
    return (int) one.getParameters().values().stream().filter(kind -> kind.equals("path")).count();
  }
}

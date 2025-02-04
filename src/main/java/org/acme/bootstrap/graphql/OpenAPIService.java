package org.acme.bootstrap.graphql;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.acme.common.projection.ExecutionNode;
import org.acme.common.projection.ExecutionTree;
import org.acme.common.projection.RelationshipDefinition;
import lombok.Getter;

@Getter
public class OpenAPIService {
  static final String PATH = "path";

//  private final OpenAPI openAPI;
  private final String serverUrl;

  public OpenAPIService(InputStream stream, String serverUrl) {
//    try (Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
//      String yaml = scanner.useDelimiter("\\A").next();
      this.serverUrl = serverUrl;
//      this.openAPI = new OpenAPIV3Parser().readContents(yaml).getOpenAPI();
//    }
    
  }

  public ExecutionTree buildExecutionTree() {
    Map<String, Map<String, ExecutionNode>> nodes = new HashMap<>();
    // Recorrer los endpoints
    Map<String, ExecutionNode> pathMap = new HashMap<>();
    // necesito tres nodos.
    // area
    ExecutionNode area = ExecutionNode.builder()
        .server(serverUrl).endpoint("/api/market/areas").method("GET").list(true)
        .build();
    area.addRelation("place", RelationshipDefinition.builder()
        .list(false)
        .id("/api/market/places")
        .url(serverUrl + "/api/market/places")
        .method("GET")
        .batchParam("uids")
        .on("$ref")
        .referenceField("uid")
        .build());
    nodes.put("areas", Map.of(area.getEndpoint(),  area));
    pathMap.put(area.getEndpoint(), area);
    // place
    ExecutionNode place = ExecutionNode.builder()
        .server(serverUrl).endpoint("/api/market/places").method("GET").list(true)
        .build();
    place.addRelation("merchant", RelationshipDefinition.builder()
        .list(false)
        .id("/api/market/merchants")
        .url(serverUrl + "/api/market/merchants")
        .method("GET")
        .batchParam("uids")
        .on("$ref")
        .referenceField("uid")
        .build());
    nodes.put("place", Map.of("/api/market/merchants", place));
    pathMap.put(place.getEndpoint(), place);
    // merchant
    ExecutionNode merchant = ExecutionNode.builder()
        .server(serverUrl).endpoint("/api/market/merchants").method("GET").list(true)
        .build();
    nodes.put("merchant", Map.of("/api/market/merchants", merchant) );
    pathMap.put(merchant.getEndpoint(), merchant);
    
    return ExecutionTree.builder().nodes(nodes).tree( pathMap ).build();

//    openAPI.getPaths().forEach((path, pathItem) -> {
//      for (PathItem.HttpMethod method : pathItem.readOperationsMap().keySet()) {
//        if (method == PathItem.HttpMethod.GET) {
//          Operation operation = pathItem.readOperationsMap().get(method);
//          boolean hasPath = null != operation.getParameters()
//              ? operation.getParameters().stream().noneMatch(pr -> pr.getIn().equals(PATH))
//              : true;
//          ExecutionNode node = ExecutionNode.builder()
//              .server(serverUrl).endpoint(path).method(method.name()).list(hasPath)
//              .build();
//          // ExecutionNode node = new ExecutionNode(serverUrl, path, method.name(), hasPath);
//          // Extraer parámetros
//          if (operation.getParameters() != null) {
//            operation.getParameters()
//                .forEach(param -> node.setParameter(param.getName(),
//                    ExecutionParam.builder().name(param.getName())
//                        .required(Boolean.TRUE.equals(param.getRequired())).in(param.getIn())
//                        .build()));
//          }
//          try {
//            Map<String, RelationshipDefinition> relationships = new HashMap<>();
//            ApiResponse apiResponse = operation.getResponses().get("200");
//            if (null != apiResponse) {
//              MediaType mediaType = apiResponse.getContent().get("application/json");
//              if (null != mediaType) {
//                String ref = mediaType.getSchema().get$ref();
//                getRelationships(hasPath, relationships, "", ref);
//                relationships.forEach(node::addRelation);
//              }
//            }
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//          pathMap.put(path, node);
//          operation.getTags().forEach(tag -> {
//            if (!nodes.containsKey(tag)) {
//              nodes.put(tag, new HashMap<>());
//            }
//            nodes.get(tag).put(path, node);
//          });
//        }
//      }
//    });
//    return ExecutionTree.builder().nodes(nodes).tree( pathMap ).build();
//  }
//
//  @SuppressWarnings({"unchecked", "rawtypes"})
//  public void getRelationships(boolean list, Map<String, RelationshipDefinition> relationships,
//      String prefix, String ref) {
//    if (ref.startsWith("#/components/schemas/")) {
//      String entity = ref.substring(21);
//      Schema schema = openAPI.getComponents().getSchemas().get(entity);
//      if (schema != null) {
//        if (list) {
//          Schema items = (Schema) schema.getProperties().get("items");
//          if (null != items) {
//            Schema childs = items.getItems();
//            getRelationships(false, relationships, prefix, childs.get$ref());
//          }
//        } else {
//          schema.getProperties().forEach((propertyName, ps) -> {
//            Schema propertySchema = (Schema) ps;
//            String refType = propertySchema.get$ref();
//            if (refType != null) {
//              getRelationships(false, relationships, (String) propertyName, refType);
//            } else if (propertySchema.getExtensions() != null
//                && propertySchema.getExtensions().containsKey("x-relationship")) {
//              Map<String, String> relationshipData =
//                  (Map<String, String>) propertySchema.getExtensions().get("x-relationship");
//              String id = relationshipData.get("url");
//              String url = id;
//              try {
//                URI uri = new URI(url);
//                if( uri.getScheme() == null || uri.getHost() == null ) {
//                  url = serverUrl + url;
//                }
//              } catch (URISyntaxException e) {
//                url = serverUrl + url;
//              }
//              RelationshipDefinition rel = RelationshipDefinition.builder()
//                  .id( id )
//                  .list(false)
//                  .url(url).method(relationshipData.get("method"))
//                  .batchParam(relationshipData.get("batch-query-param"))
//                  .on((String)propertyName)
//                  .referenceField(relationshipData.get("reference")).build();
//              relationships.put(StringUtils.isBlank(prefix) ? (String) propertyName : prefix, rel);
//            }
//          });
//        }
//      }
//    }
    // return relationships;
  }

//  private boolean hasPath(Map<String, String> params, ExecutionNode node) {
//    boolean hasAllPassed = node.getParameters().keySet().containsAll(params.keySet());
//    boolean hasAllRequired = params.keySet().containsAll(node.getParameters().values().stream()
//        .filter(ExecutionParam::isRequired).map(ExecutionParam::getName).toList());
//    return hasAllPassed && hasAllRequired;
//  }

//  private int comparePathParams(ExecutionNode one, ExecutionNode other) {
//    return countPathParams(other) - countPathParams(one);
//  }
//
//  private int countPathParams(ExecutionNode one) {
//    return (int) one.getParameters().values().stream().filter(kind -> kind.equals(PATH)).count();
//  }
}

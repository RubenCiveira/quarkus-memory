package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import graphql.language.Document;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.language.SelectionSet;
import graphql.parser.Parser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class GraphQLInterpreter {
  private final Client client = ClientBuilder.newClient();
  // private final ObjectMapper mapper;


  public Optional<ExecutionPlan> interpret(String query) {
    // Parsear la consulta GraphQL
    Parser parser = new Parser();
    Document document = parser.parseDocument(query);

    // Extraer la operación (ej: query, mutation)
    OperationDefinition operation = (OperationDefinition) document.getDefinitions().get(0);
    Map<String, String> selector = classifyQuery(operation.getSelectionSet().getSelections());
    Map<String, String> proyection = proyection(operation.getSelectionSet().getSelections());

    String operationType = operation.getOperation().name();

    String rootField = extractRootField(operation);


    OpenAPIService openApi = new OpenAPIService(
        getClass().getResourceAsStream("/META-INF/openapi/api.yaml"), "http://localhost:8090/");
    // Construir árbol de ejecución desde OpenAPI
    return openApi.buildExecutionTree(rootField, selector)
        .map(node -> ExecutionPlan.builder().node(node).selection(proyection).build());
  }

  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> execute(ExecutionPlan plan, Map<String, String> params,
      Map<String, List<String>> headers) {
    Invocation.Builder request =
        client.target(plan.getNode().target(params)).request(MediaType.APPLICATION_JSON);
    headers.forEach(request::header);
    Response response = request.get();
    List<Map<String, Object>> mappedEntity = new ArrayList<>();
    // FIXME: puede ser lista u object....
    List<Map<String, Object>> readEntity = response.readEntity(List.class);
    readEntity.forEach(map -> {
      System.out.println("\t" + map);
      Map<String,Object> row = new HashMap<>();
      plan.getSelection().forEach( (key,value) -> {
        row.put(key, map.get(value));
      });
      mappedEntity.add( row );
    });
    return mappedEntity;
  }

  private String extractRootField(OperationDefinition operation) {
    SelectionSet selectionSet = operation.getSelectionSet();
    if (selectionSet != null && !selectionSet.getSelections().isEmpty()) {
      return ((Field) selectionSet.getSelections().get(0)).getName();
    }
    return "No definido";
  }

  private Map<String, String> proyection(@SuppressWarnings("rawtypes") List<Selection> selection) {
    Map<String, String> params = new HashMap<>();
    @SuppressWarnings("rawtypes")
    Selection sel = selection.get(0);
    if (sel instanceof Field rootField) {
      rootField.getSelectionSet().getSelections().forEach(agg -> {
        if (agg instanceof Field aggField) {
          params.put(null == aggField.getAlias() ? aggField.getName() : aggField.getAlias(),
              aggField.getName());
        }
      });
    }
    return params;
  }

  private Map<String, String> classifyQuery(
      @SuppressWarnings("rawtypes") List<Selection> selection) {
    Map<String, String> params = new HashMap<>();
    @SuppressWarnings("rawtypes")
    Selection sel = selection.get(0);
    if (sel instanceof Field rootField) {
      if (rootField.getArguments() != null && !rootField.getArguments().isEmpty()) {
        rootField.getArguments().forEach(argument -> {
          params.put(argument.getName(), argument.getValue().toString());
        });
      }
    }
    return params;
  }
}

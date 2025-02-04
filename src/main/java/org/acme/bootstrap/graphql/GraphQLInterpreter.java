package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.acme.common.projection.ExecutionAggregation;
import org.acme.common.projection.ExecutionPlan;
import org.acme.common.projection.ExecutionTree;
import org.acme.common.projection.ProjectionRunner;
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
  private final ProjectionRunner runner;
  // private final ObjectMapper mapper;


  public Optional<ExecutionPlan> interpret(String query, 
      String transform,
      ExecutionTree buildExecutionTree) {
    // Parsear la consulta GraphQL
    Parser parser = new Parser();
    Document document = parser.parseDocument(query);

    // Extraer la operación (ej: query, mutation)
    OperationDefinition operation = (OperationDefinition) document.getDefinitions().get(0);
    Map<String, String> selector = classifyQuery(operation.getSelectionSet().getSelections());
    List<ExecutionAggregation> proyection =
        rootProyection(operation.getSelectionSet().getSelections());

    String operationType = operation.getOperation().name();

    String rootField = extractRootField(operation);

//    OpenAPIService openApi = new OpenAPIService(
//        getClass().getResourceAsStream("/META-INF/openapi/api.yaml"), "http://localhost:8090/");
//    ExecutionTree buildExecutionTree = openApi.buildExecutionTree();
    return buildExecutionTree.byRequest(rootField, selector)
        .map(node -> ExecutionPlan.builder()
        .tree( buildExecutionTree )
        .transformation(transform)
        .node(node).selection(proyection).build());
  }

  private String extractRootField(OperationDefinition operation) {
    SelectionSet selectionSet = operation.getSelectionSet();
    if (selectionSet != null && !selectionSet.getSelections().isEmpty()) {
      return ((Field) selectionSet.getSelections().get(0)).getName();
    }
    return "No definido";
  }

  private List<ExecutionAggregation> rootProyection(
      @SuppressWarnings("rawtypes") List<Selection> selection) {
    List<ExecutionAggregation> params = new ArrayList<>();
    if (null != selection) {
      @SuppressWarnings("rawtypes")
      Selection sel = selection.get(0);
      if (sel instanceof Field rootField) {
        proyection(rootField.getSelectionSet().getSelections(), params);
      }
    }
    return params;
  }

  private void proyection(@SuppressWarnings("rawtypes") List<Selection> selection,
      List<ExecutionAggregation> params) {
    if (null != selection) {
      selection.forEach(sel -> {
        if (sel instanceof Field aggField) {
          SelectionSet relSel = aggField.getSelectionSet();
          List<ExecutionAggregation> childs = new ArrayList<>();
          if (null != relSel) {
            proyection(relSel.getSelections(), childs);
          }
          params.add(ExecutionAggregation.builder().name(aggField.getName())
              .alias(null == aggField.getAlias() ? aggField.getName() : aggField.getAlias())
              .selection(childs).build());
        }
      });
    }
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

package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.acme.common.projection.ExecutionPlan;
import org.acme.common.projection.ExecutionTree;
import org.acme.common.projection.ProjectionRunner;
import org.eclipse.microprofile.graphql.GraphQLApi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;

@Path("/test/graphql")
@GraphQLApi
@RequiredArgsConstructor
public class GraphQLService {

  private final ProjectionRunner runner;
  private final GraphQLInterpreter graphQLInterpreter; // Servicio que interpretará la consulta

  @GET
  public Object testQl(@Context HttpHeaders httpHeaders) {
    OpenAPIService openApi = new OpenAPIService(
        getClass().getResourceAsStream("/META-INF/openapi/api.yaml"), "http://localhost:8090/");
    ExecutionTree buildExecutionTree = openApi.buildExecutionTree();
    
    List<Object> pasos = new ArrayList<>();
    String queryList =
        "query {\n" + "  area {\n" + "    codigo: uid\n" + "    name\n" + "  }\n" + "}";
    String querySearch =
        "query {\n" + "  area(search: 1) {\n" + "    uid\n" + "    name\n" + "  }\n" + "}";
    String queryItem =
        "query {\n" + "  area(uid: 1) {\n" + "    uid\n" + "    name\n" + "  }\n" + "}";
    String refList = "" 
        + "query {\n" 
        + "  area {\n" 
        + "    codigo: uid\n" 
        + "    name\n"
        + "    lugar: place {\n" 
        + "      place: name\n"
        + "      jefe: merchant {\n"
        + "        nombre: name\n"
        + "      }\n" 
        + "    }" 
        + "  }\n" + "}";
    // Optional<ExecutionPlan> interpretList = graphQLInterpreter.interpret(queryList);
    // interpretList.ifPresent(node -> {
    // Object execute = graphQLInterpreter.execute(node, Map.of(), httpHeaders.getRequestHeaders());
    // pasos.add(execute);
    // });
    long in = System.currentTimeMillis();
    Optional<ExecutionPlan> interpretRef = graphQLInterpreter.interpret(refList, ""
        + "$map($, function($v) { \r\n"
        + "  $merge([\r\n"
        + "    $v, \r\n"
        + "    { \"lugar\": $merge([$v.lugar, {\r\n"
        + "                       \"jefe\": undefined,"
        + "                       \"jefe\": $uppercase($v.lugar.jefe.nombre),"
        + "                       \"place\": $uppercase($v.lugar.place)\r\n"
        + "                 }]) }\r\n"
        + "  ])\r\n"
        + "})"
        + "", buildExecutionTree);
    long af =  System.currentTimeMillis();
    if( interpretRef.isPresent() ) {
      Object execute = runner.execute(interpretRef.get(), Map.of(), httpHeaders.getRequestHeaders());
//      Object execute = interpretRef.get().execute(Map.of(), httpHeaders.getRequestHeaders());
      pasos.add(execute);
      long ls =  System.currentTimeMillis();
      System.err.println("EL TIEMPO DE AF : " + ( af - in ));
      System.err.println("EL TIEMPO DE LS L " + ( ls - af ));
    };
    Optional<ExecutionPlan> interpretSearch = graphQLInterpreter.interpret(querySearch, "", buildExecutionTree);
    Optional<ExecutionPlan> interpretItem = graphQLInterpreter.interpret(queryItem, "", buildExecutionTree);
    // pasos.add( interpretList );
    // pasos.add( interpretSearch );
    // pasos.add( interpretItem );
    return pasos;
  }

}

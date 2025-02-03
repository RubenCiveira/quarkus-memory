package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

  private final GraphQLInterpreter graphQLInterpreter; // Servicio que interpretará la consulta

  @GET
  public Object testQl(@Context HttpHeaders httpHeaders) {
    List<Object> pasos = new ArrayList<>();
    String queryList =
        "query {\n" + "  fruit {\n" + "    codigo: uid\n" + "    name\n" + "  }\n" + "}";
    String querySearch =
        "query {\n" + "  fruit(search: 1) {\n" + "    uid\n" + "    name\n" + "  }\n" + "}";
    String queryItem =
        "query {\n" + "  fruit(uid: 1) {\n" + "    uid\n" + "    name\n" + "  }\n" + "}";
    Optional<ExecutionPlan> interpretList = graphQLInterpreter.interpret(queryList);
    interpretList.ifPresent(node -> {
      Object execute =
          graphQLInterpreter.execute(node, Map.of(), httpHeaders.getRequestHeaders());
      pasos.add(execute);
    });
    Optional<ExecutionPlan> interpretSearch = graphQLInterpreter.interpret(querySearch);
    Optional<ExecutionPlan> interpretItem = graphQLInterpreter.interpret(queryItem);
    // pasos.add( interpretList );
    // pasos.add( interpretSearch );
    // pasos.add( interpretItem );
    return pasos;
  }

}

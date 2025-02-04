package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.acme.common.projection.ExecutionAggregation;
import org.acme.common.projection.ExecutionPlan;
import org.acme.common.projection.ExecutionTree;
import org.acme.common.projection.ProjectionRunner;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;

@Path("/test/graphql")
@RequiredArgsConstructor
public class GraphQLService {

  private final ProjectionRunner runner;

  @GET
  public Object testQl(@Context HttpHeaders httpHeaders) {
    OpenAPIService openApi = new OpenAPIService(
        getClass().getResourceAsStream("/META-INF/openapi/api.yaml"), "http://localhost:8090/");
    
    long in = System.currentTimeMillis();
    ExecutionTree tree = openApi.buildExecutionTree();
    
    List<Object> pasos = new ArrayList<>();
    tree.byId("/api/market/areas").ifPresent(node -> {
      long af =  System.currentTimeMillis();

      ExecutionPlan plan = ExecutionPlan.builder()
          .node( node )
          .tree( tree )
          .selection(List.of(
              ExecutionAggregation.builder().name("uid").alias("codigo").selection(List.of()).build(),
              ExecutionAggregation.builder().name("name").alias("name").selection(List.of()).build(),
              ExecutionAggregation.builder().name("place").alias("lugar").selection(List.of(
                  ExecutionAggregation.builder().name("name").alias("place").selection(List.of()).build(),
                  ExecutionAggregation.builder().name("merchant").alias("jefe").selection(List.of(
                      ExecutionAggregation.builder().name("name").alias("nombre").selection(List.of()).build()
                  )).build()
              )).build())
          ).transformation(""
              + "  $merge([ $,{ "
              + "    \"lugar\": $merge([$.lugar, "
              + "       { \"jefe\": undefined,\"jefe\": $uppercase($.lugar.jefe.nombre), \"place\": $uppercase($.lugar.place) }"
              + "    ]) "
              + "  }])\r\n"
              + "")
          .build();
      Object execute = runner.execute(plan, Map.of(), httpHeaders.getRequestHeaders());
      pasos.add(execute);
      long ls =  System.currentTimeMillis();
      System.err.println("EL TIEMPO DE AF : " + ( af - in ));
      System.err.println("EL TIEMPO DE LS L " + ( ls - af ));
    });
    return pasos;
  }

}

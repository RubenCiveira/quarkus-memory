package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.acme.common.projection.ExecutionAggregation;
import org.acme.common.projection.ExecutionPlan;
import org.acme.common.projection.ExecutionTree;
import org.acme.common.projection.ProjectionRunner;
import org.acme.common.projection.SelfProjection;
import org.acme.features.market.area.infrastructure.bootstrap.AreaProjectionDescriptor.AreaExecutionPlanner;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;

@Path("/test/graphql")
@RequiredArgsConstructor
public class GraphQLService {

  private final ProjectionRunner runner;
  private final SelfProjection self;

  @GET
  public Object testQl(@Context HttpHeaders httpHeaders) {
    long in = System.currentTimeMillis();
    ExecutionTree tree = self.getExecutionTree();

    ExecutionPlan plan = ExecutionPlan.builder().path("/api/market/areas").tree(tree).selection(
        new AreaExecutionPlanner()
        .withUid("codigo").withName("nombre").withPlace("lugar", ofPlace -> ofPlace
            .withName("place").withMerchant("jefe", ofMerchant -> ofMerchant.withName("nombre")))
        .build() )
        .transformation(""
//            + "  $merge([ $,{ " + "    \"lugar\": $merge([$.lugar, "
//            + "       { \"jefe\": undefined,\"jefe\": $uppercase($.lugar.jefe.nombre), \"place\": $uppercase($.lugar.place) }"
//            + "    ]) " + "  }])\r\n" 
            
            + "")
        .build();
    
    return runner.execute(plan, Map.of(), httpHeaders.getRequestHeaders());
    
//    tree.byId("/api/market/areas").ifPresent(node -> {
//      long af = System.currentTimeMillis();
//
//      List<ExecutionAggregation> nobuild = List.of(
//          ExecutionAggregation.builder().name("uid").alias("codigo").selection(List.of()).build(),
//          ExecutionAggregation.builder().name("name").alias("name").selection(List.of()).build(),
//          ExecutionAggregation.builder().name("place").alias("lugar")
//              .selection(List.of(
//                  ExecutionAggregation.builder().name("name").alias("place").selection(List.of())
//                      .build(),
//                  ExecutionAggregation.builder().name("merchant").alias("jefe")
//                      .selection(List.of(ExecutionAggregation.builder().name("name").alias("nombre")
//                          .selection(List.of()).build()))
//                      .build()))
//              .build());
//
//      ExecutionPlan plan = ExecutionPlan.builder().node(node).tree(tree).selection(
//          new AreaExecutionPlanner()
//          .withUid("codigo").withName("nombre").withPlace("lugar", ofPlace -> ofPlace
//              .withName("place").withMerchant("jefe", ofMerchant -> ofMerchant.withName("nombre")))
//          .build() )
//          .transformation(""
////              + "  $merge([ $,{ " + "    \"lugar\": $merge([$.lugar, "
////              + "       { \"jefe\": undefined,\"jefe\": $uppercase($.lugar.jefe.nombre), \"place\": $uppercase($.lugar.place) }"
////              + "    ]) " + "  }])\r\n" 
//              
//              + "")
//          .build();
//      Object execute = runner.execute(plan, Map.of(), httpHeaders.getRequestHeaders());
//      pasos.add(execute);
//      long ls = System.currentTimeMillis();
//      System.err.println("EL TIEMPO DE AF : " + (af - in));
//      System.err.println("EL TIEMPO DE LS L " + (ls - af));
//    });
//    return pasos;
  }

}

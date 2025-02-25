package org.acme.features.market.area.infrastructure.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.acme.common.projection.ExecutionAggregation;
import org.acme.common.projection.ExecutionNode;
import org.acme.common.projection.ParamKind;
import org.acme.common.projection.ProjectionDescriptor;
import org.acme.common.projection.RelationshipDefinition;
import org.acme.features.market.place.infrastructure.bootstrap.PlaceProjectionDescriptor.PlaceExecutionPlanner;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AreaProjectionDescriptor implements ProjectionDescriptor {

  /**
   * @autogenerated ProjectionDescriptorGeneratorGenerator
   */
  public static class AreaExecutionPlanner {

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     */
    private final List<ExecutionAggregation> aggregation = new ArrayList<>();

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     */
    String prefix;

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     */
    public AreaExecutionPlanner() {
      this.prefix = "";
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param prefix
     */
    public AreaExecutionPlanner(final String prefix) {
      this.prefix = prefix + ".";
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public List<ExecutionAggregation> build() {
      return aggregation;
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public AreaExecutionPlanner withName() {
      return withName(prefix + "name");
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public AreaExecutionPlanner withName(final String alias) {
      aggregation.add(
          ExecutionAggregation.builder().name("name").alias(alias).selection(List.of()).build());
      return this;
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public AreaExecutionPlanner withPlace() {
      return withPlace(prefix + "place", null);
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public AreaExecutionPlanner withPlace(final String alias) {
      return withPlace(alias, null);
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param callback
     * @return
     */
    public AreaExecutionPlanner withPlace(final UnaryOperator<PlaceExecutionPlanner> callback) {
      return withPlace(prefix + "place", callback);
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @param callback
     * @return
     */
    public AreaExecutionPlanner withPlace(final String alias,
        final UnaryOperator<PlaceExecutionPlanner> callback) {
      aggregation
          .add(ExecutionAggregation.builder().name("place").alias(alias)
              .selection(null != callback
                  ? callback.apply(new PlaceExecutionPlanner(prefix + alias)).build()
                  : List.of())
              .build());
      return this;
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public AreaExecutionPlanner withUid() {
      return withUid(prefix + "uid");
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public AreaExecutionPlanner withUid(final String alias) {
      aggregation.add(
          ExecutionAggregation.builder().name("uid").alias(alias).selection(List.of()).build());
      return this;
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public AreaExecutionPlanner withVersion() {
      return withVersion(prefix + "version");
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public AreaExecutionPlanner withVersion(final String alias) {
      aggregation.add(
          ExecutionAggregation.builder().name("version").alias(alias).selection(List.of()).build());
      return this;
    }
  }

  /**
   * @autogenerated ProjectionDescriptorGeneratorGenerator
   * @param baseServer
   * @return
   */
  @Override
  public List<ExecutionNode> baseNodes(final String baseServer) {
    Map<String, RelationshipDefinition> relations = relations(baseServer);
    return List.of(
        ExecutionNode.builder().server(baseServer).endpoint("/api/market/areas").method("GET")
            .list(true).params(Map.of()).relations(relations).build(),
        ExecutionNode.builder().server(baseServer).endpoint("/api/market/areas/{uid}").method("GET")
            .list(false).params(Map.of("uid", ParamKind.PATH)).relations(relations).build());
  }

  /**
   * @autogenerated ProjectionDescriptorGeneratorGenerator
   * @param baseServer
   * @return
   */
  private Map<String, RelationshipDefinition> relations(final String baseServer) {
    return Map.of("place",
        RelationshipDefinition.builder().list(false).id("/api/market/places")
            .url(baseServer + "/api/market/places").method("GET").batchParam("uids").on("$ref")
            .referenceField("uid").build());
  }
}

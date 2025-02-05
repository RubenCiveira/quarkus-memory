package org.acme.features.market.medal.infrastructure.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.acme.common.projection.ExecutionAggregation;
import org.acme.common.projection.ExecutionNode;
import org.acme.common.projection.ParamKind;
import org.acme.common.projection.ProjectionDescriptor;
import org.acme.common.projection.RelationshipDefinition;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MedalProjectionDescriptor implements ProjectionDescriptor {

  /**
   * @autogenerated ProjectionDescriptorGeneratorGenerator
   */
  public static class MedalExecutionPlanner {

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
    public MedalExecutionPlanner() {
      this.prefix = "";
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param prefix
     */
    public MedalExecutionPlanner(final String prefix) {
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
    public MedalExecutionPlanner withName() {
      return withName(prefix + "name");
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public MedalExecutionPlanner withName(final String alias) {
      aggregation.add(
          ExecutionAggregation.builder().name("name").alias(alias).selection(List.of()).build());
      return this;
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public MedalExecutionPlanner withUid() {
      return withUid(prefix + "uid");
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public MedalExecutionPlanner withUid(final String alias) {
      aggregation.add(
          ExecutionAggregation.builder().name("uid").alias(alias).selection(List.of()).build());
      return this;
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @return
     */
    public MedalExecutionPlanner withVersion() {
      return withVersion(prefix + "version");
    }

    /**
     * @autogenerated ProjectionDescriptorGeneratorGenerator
     * @param alias
     * @return
     */
    public MedalExecutionPlanner withVersion(final String alias) {
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
        ExecutionNode.builder().server(baseServer).endpoint("/api/market/medals").method("GET")
            .list(true).params(Map.of()).relations(relations).build(),
        ExecutionNode.builder().server(baseServer).endpoint("/api/market/medals/{uid}")
            .method("GET").list(false).params(Map.of("uid", ParamKind.PATH)).relations(relations)
            .build());
  }

  /**
   * @autogenerated ProjectionDescriptorGeneratorGenerator
   * @param baseServer
   * @return
   */
  private Map<String, RelationshipDefinition> relations(final String baseServer) {
    return Map.of();
  }
}

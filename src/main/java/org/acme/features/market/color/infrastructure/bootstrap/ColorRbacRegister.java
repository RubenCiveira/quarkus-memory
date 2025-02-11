package org.acme.features.market.color.infrastructure.bootstrap;

import java.util.List;

import org.acme.common.security.Rbac;
import org.acme.common.security.scope.FieldDescription;
import org.acme.common.security.scope.ResourceDescription;
import org.acme.common.security.scope.ScopeDescription;

import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
class ColorRbacRegister {

  /**
   * @autogenerated RegisterGeneratorGenerator
   */
  private static final ResourceDescription RESOURCE =
      ResourceDescription.builder().name("color").description("color").build();

  /**
   * @autogenerated RegisterGeneratorGenerator
   */
  private final Rbac rbac;

  /**
   * @autogenerated RegisterGeneratorGenerator
   * @param ev
   */
  void registerResource(@Observes @Priority(10) final StartupEvent ev) {
    rbac.registerResourceAction(RESOURCE,
        ScopeDescription.builder().name("list").description("list").required(List.of()).build());
    rbac.registerResourceAction(RESOURCE,
        ScopeDescription.builder().name("retrieve").description("list").build());
    rbac.registerResourceAction(RESOURCE,
        ScopeDescription.builder().name("create").description("list").required(List.of()).build());
    rbac.registerResourceAction(RESOURCE,
        ScopeDescription.builder().name("update").description("list").required(List.of()).build());
    rbac.registerResourceAction(RESOURCE,
        ScopeDescription.builder().name("delete").description("list").required(List.of()).build());
    rbac.registerResourceField(RESOURCE, FieldDescription.builder().name("uid")
        .description("A number to identify the db record").build());
    rbac.registerResourceField(RESOURCE,
        FieldDescription.builder().name("name").description("El name de color").build());
    rbac.registerResourceField(RESOURCE,
        FieldDescription.builder().name("merchant").description("El merchant de color").build());
    rbac.registerResourceField(RESOURCE,
        FieldDescription.builder().name("version")
            .description(
                "Campo con el número de version de color para controlar bloqueos optimistas")
            .build());
  }
}

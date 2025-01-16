package org.acme.features.market.area.infrastructure.driver.rest;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Interaction;
import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.area.application.service.AreasVisibilityService;
import org.acme.features.market.area.application.usecase.create.CreateAreaUsecase;
import org.acme.features.market.area.application.usecase.delete.DeleteAreaUsecase;
import org.acme.features.market.area.application.usecase.list.ListAreaUsecase;
import org.acme.features.market.area.application.usecase.retrieve.RetrieveAreaUsecase;
import org.acme.features.market.area.application.usecase.update.UpdateAreaUsecase;
import org.acme.generated.openapi.api.AreaAclApi;
import org.acme.generated.openapi.model.AreaAclFields;
import org.acme.generated.openapi.model.AreaAclGenericAllows;
import org.acme.generated.openapi.model.AreaAclSpecificAllows;
import org.acme.generated.openapi.model.AreaGenericAcl;
import org.acme.generated.openapi.model.AreaSpecificAcl;
import org.acme.generated.openapi.model.CommonAllow;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class AreaAclController implements AreaAclApi {

  /**
   * @autogenerated AclControllerGenerator
   */
  private final CreateAreaUsecase create;

  /**
   * Area
   *
   * @autogenerated AclControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final DeleteAreaUsecase delete;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final ListAreaUsecase list;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final RetrieveAreaUsecase retrieve;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final UpdateAreaUsecase update;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final AreasVisibilityService visibility;

  /**
   * @autogenerated AclControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response areaApiContextualAcl(final String uid) {
    return currentRequest.resolve(interaction -> {
      AreaSpecificAcl response = new AreaSpecificAcl();
      response.setAllows(new AreaAclSpecificAllows());
      response.setFields(new AreaAclFields());
      return CompletableFuture
          .allOf(fixedFields(response.getFields(), interaction),
              hiddenFields(response.getFields(), interaction), updateAllows(response, interaction),
              deleteAllows(response, interaction), retrieveAllows(response, interaction))
          .thenApply(noop -> response);
    });
  }

  /**
   * @autogenerated AclControllerGenerator
   * @return
   */
  @Override
  public Response areaApiGenericAcl() {
    return currentRequest.resolve(interaction -> {
      AreaGenericAcl response = new AreaGenericAcl();
      response.setAllows(new AreaAclGenericAllows());
      response.setFields(new AreaAclFields());
      return CompletableFuture
          .allOf(fixedFields(response.getFields(), interaction),
              hiddenFields(response.getFields(), interaction), listAllows(response, interaction),
              createAllows(response, interaction), updateAllows(response, interaction),
              deleteAllows(response, interaction), retrieveAllows(response, interaction))
          .thenApply(noop -> response);
    });
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> createAllows(final AreaGenericAcl response,
      final Interaction query) {
    return create.allow(query)
        .thenAccept(detail -> response.getAllows().setCreate(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> deleteAllows(final AreaGenericAcl response,
      final Interaction query) {
    return delete.allow(query)
        .thenAccept(detail -> response.getAllows().setDelete(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> deleteAllows(final AreaSpecificAcl response,
      final Interaction query) {
    return delete.allow(query)
        .thenAccept(detail -> response.getAllows().setDelete(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> fixedFields(final AreaAclFields response,
      final Interaction query) {
    return visibility.fieldsToFix(query).getFixed()
        .thenAccept(fields -> response.setNoEditables(new ArrayList<>(fields)))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> hiddenFields(final AreaAclFields response,
      final Interaction query) {
    return visibility.fieldsToHide(query).getHidden()
        .thenAccept(fields -> response.setNoVisibles(new ArrayList<>(fields)))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> listAllows(final AreaGenericAcl response,
      final Interaction query) {
    return list.allow(query)
        .thenAccept(detail -> response.getAllows()
            .setList(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> retrieveAllows(final AreaGenericAcl response,
      final Interaction query) {
    return retrieve.allow(query)
        .thenAccept(detail -> response.getAllows().setRetrieve(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> retrieveAllows(final AreaSpecificAcl response,
      final Interaction query) {
    return retrieve.allow(query)
        .thenAccept(detail -> response.getAllows().setRetrieve(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> updateAllows(final AreaGenericAcl response,
      final Interaction query) {
    return update.allow(query)
        .thenAccept(detail -> response.getAllows().setUpdate(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> updateAllows(final AreaSpecificAcl response,
      final Interaction query) {
    return update.allow(query)
        .thenAccept(detail -> response.getAllows().setUpdate(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }
}
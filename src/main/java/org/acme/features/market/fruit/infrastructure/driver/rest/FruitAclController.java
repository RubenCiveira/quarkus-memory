package org.acme.features.market.fruit.infrastructure.driver.rest;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Interaction;
import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.fruit.application.interaction.query.FruitAllowQuery;
import org.acme.features.market.fruit.application.interaction.query.FruitEntityAllowQuery;
import org.acme.features.market.fruit.application.usecase.CreateFruitUsecase;
import org.acme.features.market.fruit.application.usecase.DeleteFruitUsecase;
import org.acme.features.market.fruit.application.usecase.ListFruitUsecase;
import org.acme.features.market.fruit.application.usecase.RetrieveFruitUsecase;
import org.acme.features.market.fruit.application.usecase.UpdateFruitUsecase;
import org.acme.features.market.fruit.application.usecase.service.FruitsVisibilityService;
import org.acme.features.market.fruit.domain.model.FruitReference;
import org.acme.generated.openapi.api.FruitAclApi;
import org.acme.generated.openapi.model.CommonAllow;
import org.acme.generated.openapi.model.FruitAclFields;
import org.acme.generated.openapi.model.FruitAclGenericAllows;
import org.acme.generated.openapi.model.FruitAclSpecificAllows;
import org.acme.generated.openapi.model.FruitGenericAcl;
import org.acme.generated.openapi.model.FruitSpecificAcl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitAclController implements FruitAclApi {

  /**
   * @autogenerated AclControllerGenerator
   */
  private final CreateFruitUsecase create;

  /**
   * Fruit
   *
   * @autogenerated AclControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final DeleteFruitUsecase delete;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final ListFruitUsecase list;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final RetrieveFruitUsecase retrieve;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final UpdateFruitUsecase update;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final FruitsVisibilityService visibility;

  /**
   * @autogenerated AclControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response fruitApiContextualAcl(final String uid) {
    return currentRequest.resolve(interaction -> {
      FruitEntityAllowQuery query =
          FruitEntityAllowQuery.builder().reference(FruitReference.of(uid)).build(interaction);
      FruitSpecificAcl response = new FruitSpecificAcl();
      response.setAllows(new FruitAclSpecificAllows());
      response.setFields(new FruitAclFields());
      return CompletableFuture
          .allOf(fixedFields(response.getFields(), query),
              hiddenFields(response.getFields(), query), updateAllows(response, query),
              deleteAllows(response, query), retrieveAllows(response, query))
          .thenApply(noop -> response);
    });
  }

  /**
   * @autogenerated AclControllerGenerator
   * @return
   */
  @Override
  public Response fruitApiGenericAcl() {
    return currentRequest.resolve(interaction -> {
      FruitAllowQuery query = FruitAllowQuery.builder().build(interaction);
      FruitEntityAllowQuery entityQuery = FruitEntityAllowQuery.builder().build(interaction);
      FruitGenericAcl response = new FruitGenericAcl();
      response.setAllows(new FruitAclGenericAllows());
      response.setFields(new FruitAclFields());
      return CompletableFuture
          .allOf(fixedFields(response.getFields(), query),
              hiddenFields(response.getFields(), query), listAllows(response, query),
              createAllows(response, query), updateAllows(response, entityQuery),
              deleteAllows(response, entityQuery), retrieveAllows(response, entityQuery))
          .thenApply(noop -> response);
    });
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   * @return
   */
  private CompletableFuture<Void> createAllows(final FruitGenericAcl response,
      final FruitAllowQuery query) {
    return create.allow(query).getDetail()
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
  private CompletableFuture<Void> deleteAllows(final FruitGenericAcl response,
      final FruitEntityAllowQuery query) {
    return delete.allow(query).getDetail()
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
  private CompletableFuture<Void> deleteAllows(final FruitSpecificAcl response,
      final FruitEntityAllowQuery query) {
    return delete.allow(query).getDetail()
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
  private CompletableFuture<Void> fixedFields(final FruitAclFields response,
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
  private CompletableFuture<Void> hiddenFields(final FruitAclFields response,
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
  private CompletableFuture<Void> listAllows(final FruitGenericAcl response,
      final FruitAllowQuery query) {
    return list.allow(query).getDetail()
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
  private CompletableFuture<Void> retrieveAllows(final FruitGenericAcl response,
      final FruitEntityAllowQuery query) {
    return retrieve.allow(query).getDetail()
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
  private CompletableFuture<Void> retrieveAllows(final FruitSpecificAcl response,
      final FruitEntityAllowQuery query) {
    return retrieve.allow(query).getDetail()
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
  private CompletableFuture<Void> updateAllows(final FruitGenericAcl response,
      final FruitEntityAllowQuery query) {
    return update.allow(query).getDetail()
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
  private CompletableFuture<Void> updateAllows(final FruitSpecificAcl response,
      final FruitEntityAllowQuery query) {
    return update.allow(query).getDetail()
        .thenAccept(detail -> response.getAllows().setUpdate(
            new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription())))
        .toCompletableFuture();
  }
}

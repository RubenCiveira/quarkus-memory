package org.acme.features.market.color.infrastructure.driver.rest;

import java.util.ArrayList;

import org.acme.common.action.Interaction;
import org.acme.common.infrastructure.CurrentRequest;
import org.acme.common.security.Allow;
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.application.usecase.create.CreateColorUsecase;
import org.acme.features.market.color.application.usecase.delete.DeleteColorUsecase;
import org.acme.features.market.color.application.usecase.list.ListColorUsecase;
import org.acme.features.market.color.application.usecase.retrieve.RetrieveColorUsecase;
import org.acme.features.market.color.application.usecase.update.UpdateColorUsecase;
import org.acme.generated.openapi.api.ColorAclApi;
import org.acme.generated.openapi.model.ColorAclFields;
import org.acme.generated.openapi.model.ColorAclGenericAllows;
import org.acme.generated.openapi.model.ColorAclSpecificAllows;
import org.acme.generated.openapi.model.ColorGenericAcl;
import org.acme.generated.openapi.model.ColorSpecificAcl;
import org.acme.generated.openapi.model.CommonAllow;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ColorAclController implements ColorAclApi {

  /**
   * @autogenerated AclControllerGenerator
   */
  private final CreateColorUsecase create;

  /**
   * Color
   *
   * @autogenerated AclControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final DeleteColorUsecase delete;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final ListColorUsecase list;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final RetrieveColorUsecase retrieve;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final UpdateColorUsecase update;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated AclControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response colorApiContextualAcl(final String uid) {
    Interaction interaction = currentRequest.interaction();
    ColorSpecificAcl response = new ColorSpecificAcl();
    response.setAllows(new ColorAclSpecificAllows());
    response.setFields(new ColorAclFields());
    response.getFields().setNoEditables(new ArrayList<>());
    response.getFields().setNoVisibles(new ArrayList<>());
    fixedFields(response.getFields(), interaction);
    hiddenFields(response.getFields(), interaction);
    updateAllows(response, interaction);
    deleteAllows(response, interaction);
    retrieveAllows(response, interaction);
    return Response.ok(response).build();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @return
   */
  @Override
  public Response colorApiGenericAcl() {
    Interaction interaction = currentRequest.interaction();
    ColorGenericAcl response = new ColorGenericAcl();
    response.setAllows(new ColorAclGenericAllows());
    response.setFields(new ColorAclFields());
    response.getFields().setNoEditables(new ArrayList<>());
    response.getFields().setNoVisibles(new ArrayList<>());
    fixedFields(response.getFields(), interaction);
    hiddenFields(response.getFields(), interaction);
    listAllows(response, interaction);
    createAllows(response, interaction);
    updateAllows(response, interaction);
    deleteAllows(response, interaction);
    retrieveAllows(response, interaction);
    return Response.ok(response).build();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void createAllows(final ColorGenericAcl response, final Interaction query) {
    Allow detail = create.allow(query);
    response.getAllows()
        .setCreate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void deleteAllows(final ColorGenericAcl response, final Interaction query) {
    Allow detail = delete.allow(query);
    response.getAllows()
        .setDelete(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void deleteAllows(final ColorSpecificAcl response, final Interaction query) {
    Allow detail = delete.allow(query);
    response.getAllows()
        .setDelete(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void fixedFields(final ColorAclFields response, final Interaction query) {
    visibility.fieldsToFix(query).forEach(field -> response.getNoEditables().add(field));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void hiddenFields(final ColorAclFields response, final Interaction query) {
    visibility.fieldsToHide(query).forEach(field -> response.getNoVisibles().add(field));;
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void listAllows(final ColorGenericAcl response, final Interaction query) {
    Allow detail = list.allow(query);
    response.getAllows()
        .setList(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void retrieveAllows(final ColorGenericAcl response, final Interaction query) {
    Allow detail = retrieve.allow(query);
    response.getAllows()
        .setRetrieve(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void retrieveAllows(final ColorSpecificAcl response, final Interaction query) {
    Allow detail = retrieve.allow(query);
    response.getAllows()
        .setRetrieve(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void updateAllows(final ColorGenericAcl response, final Interaction query) {
    Allow detail = update.allow(query);
    response.getAllows()
        .setUpdate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void updateAllows(final ColorSpecificAcl response, final Interaction query) {
    Allow detail = update.allow(query);
    response.getAllows()
        .setUpdate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }
}

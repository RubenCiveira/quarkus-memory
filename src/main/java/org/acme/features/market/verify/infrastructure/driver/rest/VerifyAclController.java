package org.acme.features.market.verify.infrastructure.driver.rest;

import java.util.ArrayList;

import org.acme.common.action.Interaction;
import org.acme.common.infrastructure.CurrentRequest;
import org.acme.common.security.Allow;
import org.acme.features.market.verify.application.service.VerifysVisibilityService;
import org.acme.features.market.verify.application.usecase.create.CreateVerifyUsecase;
import org.acme.features.market.verify.application.usecase.delete.DeleteVerifyUsecase;
import org.acme.features.market.verify.application.usecase.list.ListVerifyUsecase;
import org.acme.features.market.verify.application.usecase.retrieve.RetrieveVerifyUsecase;
import org.acme.features.market.verify.application.usecase.update.UpdateVerifyUsecase;
import org.acme.generated.openapi.api.VerifyAclApi;
import org.acme.generated.openapi.model.CommonAllow;
import org.acme.generated.openapi.model.VerifyAclFields;
import org.acme.generated.openapi.model.VerifyAclGenericAllows;
import org.acme.generated.openapi.model.VerifyAclSpecificAllows;
import org.acme.generated.openapi.model.VerifyGenericAcl;
import org.acme.generated.openapi.model.VerifySpecificAcl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class VerifyAclController implements VerifyAclApi {

  /**
   * @autogenerated AclControllerGenerator
   */
  private final CreateVerifyUsecase create;

  /**
   * Verify
   *
   * @autogenerated AclControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final DeleteVerifyUsecase delete;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final ListVerifyUsecase list;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final RetrieveVerifyUsecase retrieve;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final UpdateVerifyUsecase update;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final VerifysVisibilityService visibility;

  /**
   * @autogenerated AclControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response verifyApiContextualAcl(final String uid) {
    Interaction interaction = currentRequest.interaction();
    VerifySpecificAcl response = new VerifySpecificAcl();
    response.setAllows(new VerifyAclSpecificAllows());
    response.setFields(new VerifyAclFields());
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
  public Response verifyApiGenericAcl() {
    Interaction interaction = currentRequest.interaction();
    VerifyGenericAcl response = new VerifyGenericAcl();
    response.setAllows(new VerifyAclGenericAllows());
    response.setFields(new VerifyAclFields());
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
  private void createAllows(final VerifyGenericAcl response, final Interaction query) {
    Allow detail = create.allow(query);
    response.getAllows()
        .setCreate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void deleteAllows(final VerifyGenericAcl response, final Interaction query) {
    Allow detail = delete.allow(query);
    response.getAllows()
        .setDelete(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void deleteAllows(final VerifySpecificAcl response, final Interaction query) {
    Allow detail = delete.allow(query);
    response.getAllows()
        .setDelete(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void fixedFields(final VerifyAclFields response, final Interaction query) {
    visibility.fieldsToFix(query).forEach(field -> response.getNoEditables().add(field));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void hiddenFields(final VerifyAclFields response, final Interaction query) {
    visibility.fieldsToHide(query).forEach(field -> response.getNoVisibles().add(field));;
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void listAllows(final VerifyGenericAcl response, final Interaction query) {
    Allow detail = list.allow(query);
    response.getAllows()
        .setList(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void retrieveAllows(final VerifyGenericAcl response, final Interaction query) {
    Allow detail = retrieve.allow(query);
    response.getAllows()
        .setRetrieve(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void retrieveAllows(final VerifySpecificAcl response, final Interaction query) {
    Allow detail = retrieve.allow(query);
    response.getAllows()
        .setRetrieve(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void updateAllows(final VerifyGenericAcl response, final Interaction query) {
    Allow detail = update.allow(query);
    response.getAllows()
        .setUpdate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void updateAllows(final VerifySpecificAcl response, final Interaction query) {
    Allow detail = update.allow(query);
    response.getAllows()
        .setUpdate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }
}

package org.acme.features.market.merchant.infrastructure.driver.rest;

import java.util.ArrayList;

import org.acme.common.action.Interaction;
import org.acme.common.infrastructure.CurrentRequest;
import org.acme.common.security.Allow;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.application.usecase.create.CreateMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.delete.DeleteMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.disable.DisableMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.enable.EnableMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.list.ListMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.retrieve.RetrieveMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.update.UpdateMerchantUsecase;
import org.acme.generated.openapi.api.MerchantAclApi;
import org.acme.generated.openapi.model.CommonAllow;
import org.acme.generated.openapi.model.MerchantAclFields;
import org.acme.generated.openapi.model.MerchantAclGenericAllows;
import org.acme.generated.openapi.model.MerchantAclSpecificAllows;
import org.acme.generated.openapi.model.MerchantGenericAcl;
import org.acme.generated.openapi.model.MerchantSpecificAcl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MerchantAclController implements MerchantAclApi {

  /**
   * @autogenerated AclControllerGenerator
   */
  private final CreateMerchantUsecase create;

  /**
   * Merchant
   *
   * @autogenerated AclControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final DeleteMerchantUsecase delete;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final DisableMerchantUsecase disable;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final EnableMerchantUsecase enable;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final ListMerchantUsecase list;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final RetrieveMerchantUsecase retrieve;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final UpdateMerchantUsecase update;

  /**
   * @autogenerated AclControllerGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated AclControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response merchantApiContextualAcl(final String uid) {
    Interaction interaction = currentRequest.interaction();
    MerchantSpecificAcl response = new MerchantSpecificAcl();
    response.setAllows(new MerchantAclSpecificAllows());
    response.setFields(new MerchantAclFields());
    response.getFields().setNoEditables(new ArrayList<>());
    response.getFields().setNoVisibles(new ArrayList<>());
    fixedFields(response.getFields(), interaction);
    hiddenFields(response.getFields(), interaction);
    updateAllows(response, interaction);
    deleteAllows(response, interaction);
    retrieveAllows(response, interaction);
    enableAllows(response, interaction);
    disableAllows(response, interaction);
    return Response.ok(response).build();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @return
   */
  @Override
  public Response merchantApiGenericAcl() {
    Interaction interaction = currentRequest.interaction();
    MerchantGenericAcl response = new MerchantGenericAcl();
    response.setAllows(new MerchantAclGenericAllows());
    response.setFields(new MerchantAclFields());
    response.getFields().setNoEditables(new ArrayList<>());
    response.getFields().setNoVisibles(new ArrayList<>());
    fixedFields(response.getFields(), interaction);
    hiddenFields(response.getFields(), interaction);
    listAllows(response, interaction);
    createAllows(response, interaction);
    updateAllows(response, interaction);
    deleteAllows(response, interaction);
    retrieveAllows(response, interaction);
    enableAllows(response, interaction);
    disableAllows(response, interaction);
    return Response.ok(response).build();
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void createAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = create.allow(query);
    response.getAllows()
        .setCreate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void deleteAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = delete.allow(query);
    response.getAllows()
        .setDelete(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void deleteAllows(final MerchantSpecificAcl response, final Interaction query) {
    Allow detail = delete.allow(query);
    response.getAllows()
        .setDelete(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void disableAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = disable.allow(query);
    response.getAllows()
        .setDisable(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void disableAllows(final MerchantSpecificAcl response, final Interaction query) {
    Allow detail = disable.allow(query);
    response.getAllows()
        .setDisable(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void enableAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = enable.allow(query);
    response.getAllows()
        .setEnable(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void enableAllows(final MerchantSpecificAcl response, final Interaction query) {
    Allow detail = enable.allow(query);
    response.getAllows()
        .setEnable(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void fixedFields(final MerchantAclFields response, final Interaction query) {
    visibility.fieldsToFix(query).forEach(field -> response.getNoEditables().add(field));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void hiddenFields(final MerchantAclFields response, final Interaction query) {
    visibility.fieldsToHide(query).forEach(field -> response.getNoVisibles().add(field));;
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void listAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = list.allow(query);
    response.getAllows()
        .setList(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void retrieveAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = retrieve.allow(query);
    response.getAllows()
        .setRetrieve(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void retrieveAllows(final MerchantSpecificAcl response, final Interaction query) {
    Allow detail = retrieve.allow(query);
    response.getAllows()
        .setRetrieve(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void updateAllows(final MerchantGenericAcl response, final Interaction query) {
    Allow detail = update.allow(query);
    response.getAllows()
        .setUpdate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }

  /**
   * @autogenerated AclControllerGenerator
   * @param response
   * @param query
   */
  private void updateAllows(final MerchantSpecificAcl response, final Interaction query) {
    Allow detail = update.allow(query);
    response.getAllows()
        .setUpdate(new CommonAllow().allowed(detail.isAllowed()).reason(detail.getDescription()));
  }
}

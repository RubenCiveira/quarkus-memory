/* @autogenerated */
package org.acme.common.rest.exceptionmapper.sql;

import java.util.HashMap;
import java.util.Map;

import org.acme.common.sql.NotExistentReferenceException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class NotExistentReferenceExceptionMapper
    implements ExceptionMapper<NotExistentReferenceException> {

  @Override
  public Response toResponse(NotExistentReferenceException exception) {
    if (log.isDebugEnabled()) {
      log.info("not found exception", exception);
    } else if (log.isInfoEnabled()) {
      log.info("not found exception");
    }
    Map<String, String> error = new HashMap<>();
    error.put("reason", exception.getMessage());
    return Response.status(422).entity(error).build();
  }
}
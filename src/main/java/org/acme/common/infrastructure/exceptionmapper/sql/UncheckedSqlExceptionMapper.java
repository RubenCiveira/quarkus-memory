/* @autogenerated */
package org.acme.common.infrastructure.exceptionmapper.sql;

import java.util.HashMap;
import java.util.Map;

import org.acme.common.infrastructure.sql.UncheckedSqlException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class UncheckedSqlExceptionMapper implements ExceptionMapper<UncheckedSqlException> {

  @Override
  public Response toResponse(UncheckedSqlException exception) {
    if (log.isDebugEnabled()) {
      log.info("not found exception", exception);
    } else if (log.isInfoEnabled()) {
      log.info("not found exception");
    }
    Map<String, String> error = new HashMap<>();
    error.put("reason", exception.getMessage());
    return Response.status(500).entity(error).build();
  }
}

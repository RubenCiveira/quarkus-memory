package org.acme.bootstrap.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class SecurityFilter implements ContainerRequestFilter {
  // Definición de patrones para detectar ataques

  private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
      "(?i)\\b(SELECT|UNION|INSERT|UPDATE|DELETE|DROP|CREATE|ALTER|EXEC|MERGE|CALL)\\b.*['\";]|(--|#)");

  private static final Pattern PATH_TRAVERSAL_PATTERN = Pattern.compile("(\\.\\./|\\.\\.\\\\)");

  private static final Pattern XSS_PATTERN =
      Pattern.compile("(?i)<script.*?>|javascript:|<.*?on[a-z]+=.*?>");

  private static final Pattern COMMAND_INJECTION_PATTERN = Pattern.compile(
      "(?i)(\\b(ls|cat|rm|netstat|sudo|bash|sh|python|perl)\\b\\s*(&&|\\||;|>|>>)|(;|&&)\\s*(ls|cat|rm|sudo|bash|sh|python|perl))");

  private static final Pattern RCE_PATTERN = Pattern.compile(
      "(?i)(System\\.(exec|getRuntime)|Runtime\\.getRuntime\\(\\)|eval\\(|os\\.system|subprocess\\.|\\.__class__)");

  private static final Pattern LDAP_INJECTION_PATTERN = Pattern.compile(
      "(?i)(\\(&|\\|\\(|!|\\*\\)|\\b(objectClass|uid|cn)=\\*|\\b(|\\)|\\()(objectClass=\\*|uid=\\*|cn=\\*).*)");

  private static final Pattern HTTP_HEADER_INJECTION_PATTERN =
      Pattern.compile("(?i)(%3C|%3E|\\bjavascript:|\\b<.*?on[a-z]+=.*?>)");

  private static final Pattern XML_INJECTION_PATTERN =
      Pattern.compile("(?i)(<!ENTITY|<!DOCTYPE|file://)");

  private static final Pattern SPECIAL_CHARACTERS_PATTERN =
      Pattern.compile("(%[0-9a-fA-F]{2}|\\\\x[0-9a-fA-F]{2})");

  private final MeterRegistry meterRegistry;

  public SecurityFilter(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    int riskScore = 0;

    // Análisis de headers
    for (String header : requestContext.getHeaders().keySet()) {
      String value = requestContext.getHeaderString(header);
      riskScore += analyzeValue(value);
    }
    for (Entry<String, List<String>> entry : requestContext.getUriInfo().getQueryParameters()
        .entrySet()) {
      for (String value : entry.getValue()) {
        riskScore += analyzeValue(value);
      }
    }
    // Análisis del cuerpo (si es JSON o texto plano)
    String body = extractRequestBody(requestContext);
    if (body != null && !body.isEmpty()) {
      riskScore += analyzeValue(body);
    }

    // Skipping for brevity.
    if (riskScore > 10) {
      // Umbral arbitrario, puedes ajustar esto
      // Registrar métrica en Micrometer
      meterRegistry
          .counter("request.security.risk", "source", requestContext.getUriInfo().getPath())
          .increment();
    }
    if (riskScore > 20) {
      // Bloquear la solicitud
      requestContext.abortWith(
          Response.status(Status.FORBIDDEN).entity("Request blocked due to security risk").build());
      // Si hay muchos => bloqueamos.
    }
  }

  private int analyzeValue(String value) {
    int riskScore = 0;
    if (value == null || value.isEmpty()) {
      return riskScore;
    }

    // Detectar patrones específicos y aumentar el puntaje de riesgo
    if (SQL_INJECTION_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como SQL");
      riskScore += 5;
    }
    if (PATH_TRAVERSAL_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como PATH_TRAVERSAL");
      riskScore += 5;
    }
    if (XSS_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como XSS_TRAVERSAL");
      riskScore += 5;
    }
    if (COMMAND_INJECTION_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como COMMAND_INJECTION_PATTERN");
      riskScore += 5;
    }
    if (RCE_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como RCE_PATTERN");
      riskScore += 5;
    }
    if (LDAP_INJECTION_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como LDAP_INJECTION_PATTERN");
      riskScore += 3;
    }
    if (HTTP_HEADER_INJECTION_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como HTTP_HEADER_INJECTION_PATTERN");
      riskScore += 3;
    }
    if (XML_INJECTION_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como XML_INJECTION_PATTERN");
      riskScore += 4;
    }
    if (SPECIAL_CHARACTERS_PATTERN.matcher(value).find()) {
      System.out.println("El valor " + value + " entra como SPECIAL_CHARACTERS_PATTERN");
      riskScore += 2;
    }

    return riskScore;
  }

  private String extractRequestBody(ContainerRequestContext requestContext) throws IOException {
    // Leer el InputStream de la solicitud
    InputStream originalInputStream = requestContext.getEntityStream();
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    byte[] data = new byte[1024];
    int nRead;
    while ((nRead = originalInputStream.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }
    buffer.flush();

    // Convertir el buffer a String
    String body = buffer.toString(StandardCharsets.UTF_8);

    // Volver a asignar el InputStream al contexto para futuras lecturas
    InputStream newInputStream = new ByteArrayInputStream(buffer.toByteArray());
    requestContext.setEntityStream(newInputStream);

    return body;
  }
}

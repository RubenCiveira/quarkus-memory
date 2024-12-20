package org.acme.common.security;

import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Connection {
  private final ZonedDateTime startTime = ZonedDateTime.now();
  private final Locale locale;
  @NonNull
  private final String request;
  private final String remote;
  private final String remoteApplication;

  public Optional<String> getRemoteApplication() {
    return Optional.ofNullable(remoteApplication);
  }

  public Optional<String> getRemote() {
    return Optional.ofNullable(remote);
  }

  public Locale getLocale() {
    return locale == null ? locale : Locale.getDefault();
  }

}

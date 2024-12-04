package org.acme.common.validation.validator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class URLValidator implements Validator<String> {
  private final String errorMessage;

  public URLValidator(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String url) {
    if (url == null || url.isEmpty()) {
      return new ValidationResult(errorMessage);
    }
    try {
      new URI(url).toURL();
      return new ValidationResult();
    } catch (MalformedURLException | URISyntaxException e) {
      return new ValidationResult(errorMessage);
    }
  }
}

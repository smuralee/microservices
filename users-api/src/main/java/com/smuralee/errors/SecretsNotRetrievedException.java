package com.smuralee.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Unable to fetch the secrets from secret manager")
public class SecretsNotRetrievedException extends RuntimeException {

}

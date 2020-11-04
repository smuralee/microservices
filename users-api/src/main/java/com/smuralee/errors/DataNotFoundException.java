package com.smuralee.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No data found")
public class DataNotFoundException extends RuntimeException {

}

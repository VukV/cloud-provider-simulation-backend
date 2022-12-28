package com.raf.cloudproviderbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Both dates must be selected.")
public class SearchDateException extends RuntimeException{
}

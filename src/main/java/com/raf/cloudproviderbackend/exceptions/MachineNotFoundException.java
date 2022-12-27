package com.raf.cloudproviderbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Machine not found.")
public class MachineNotFoundException extends RuntimeException{
}

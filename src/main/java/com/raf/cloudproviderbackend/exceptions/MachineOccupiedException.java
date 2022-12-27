package com.raf.cloudproviderbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Machine already occupied.")
public class MachineOccupiedException extends RuntimeException{
}

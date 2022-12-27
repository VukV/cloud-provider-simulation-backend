package com.raf.cloudproviderbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "You don't have machine ownership.")
public class MachineOwnershipException extends RuntimeException{
}

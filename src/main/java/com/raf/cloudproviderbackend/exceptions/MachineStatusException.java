package com.raf.cloudproviderbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MachineStatusException extends ResponseStatusException {

    public MachineStatusException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }
}

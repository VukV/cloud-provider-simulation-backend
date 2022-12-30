package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;

import java.io.Serializable;

public class MachineQueueDto implements Serializable {

    private Long machineId;
    private MachineActionEnum action;
    private String userEmail;

    public MachineQueueDto() {
    }

    public MachineQueueDto(Long machineId, MachineActionEnum action, String userEmail) {
        this.machineId = machineId;
        this.action = action;
        this.userEmail = userEmail;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public MachineActionEnum getAction() {
        return action;
    }

    public void setAction(MachineActionEnum action) {
        this.action = action;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

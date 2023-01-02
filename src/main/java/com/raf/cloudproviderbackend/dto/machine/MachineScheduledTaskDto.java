package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;

public class MachineScheduledTaskDto {

    private Long machineId;
    private MachineActionEnum action;
    private String email;

    public MachineScheduledTaskDto(Long machineId, MachineActionEnum action, String email) {
        this.machineId = machineId;
        this.action = action;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

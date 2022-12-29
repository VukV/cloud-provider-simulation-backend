package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;

public class MachineErrorDto {

    private Long machineErrorId;
    private Long errorDate;
    private MachineActionEnum action;
    private String message;

    private Long machineId;
    private String machineName;

    public Long getMachineErrorId() {
        return machineErrorId;
    }

    public void setMachineErrorId(Long machineErrorId) {
        this.machineErrorId = machineErrorId;
    }

    public Long getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Long errorDate) {
        this.errorDate = errorDate;
    }

    public MachineActionEnum getAction() {
        return action;
    }

    public void setAction(MachineActionEnum action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}

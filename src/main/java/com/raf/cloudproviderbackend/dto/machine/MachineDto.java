package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.machine.MachineStatusEnum;

import java.time.LocalDateTime;

public class MachineDto {

    private Long machineId;
    private MachineStatusEnum machineStatus;
    private Long createdDate;
    private String name;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public MachineStatusEnum getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(MachineStatusEnum machineStatus) {
        this.machineStatus = machineStatus;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

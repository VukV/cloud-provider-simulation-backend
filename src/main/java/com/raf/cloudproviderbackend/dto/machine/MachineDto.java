package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.MachineStatusEnum;

import java.time.LocalDateTime;

public class MachineDto {

    private Long machineId;
    private MachineStatusEnum machineStatus;
    private LocalDateTime createdDate;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

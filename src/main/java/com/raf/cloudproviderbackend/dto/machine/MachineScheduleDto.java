package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;
import org.hibernate.annotations.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MachineScheduleDto {

    @NotNull
    private Long machineId;
    @NotNull
    @Type(type = "timestamp")
    private Long scheduleDate;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Long scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}

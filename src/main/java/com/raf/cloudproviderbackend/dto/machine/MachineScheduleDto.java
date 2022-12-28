package com.raf.cloudproviderbackend.dto.machine;

import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;
import org.hibernate.annotations.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class MachineScheduleDto {

    @NotNull
    private Long machineId;
    @NotNull @NotBlank @NotEmpty
    @Type(type = "timestamp")
    private Date scheduleDate;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}

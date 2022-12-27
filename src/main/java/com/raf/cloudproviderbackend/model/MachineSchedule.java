package com.raf.cloudproviderbackend.model;

import javax.persistence.*;

@Entity
@Table(name = "machine_schedules")
public class MachineSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineScheduleId;

    //TODO


    public Long getMachineScheduleId() {
        return machineScheduleId;
    }

    public void setMachineScheduleId(Long machineScheduleId) {
        this.machineScheduleId = machineScheduleId;
    }
}

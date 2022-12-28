package com.raf.cloudproviderbackend.model.machine;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "machine_schedules")
public class MachineSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineScheduleId;
    @Column(nullable = false)
    private LocalDateTime scheduledDate;
    @Column(nullable = false)
    private MachineActionEnum action;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Machine machine;


    public Long getMachineScheduleId() {
        return machineScheduleId;
    }

    public void setMachineScheduleId(Long machineScheduleId) {
        this.machineScheduleId = machineScheduleId;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public MachineActionEnum getAction() {
        return action;
    }

    public void setAction(MachineActionEnum action) {
        this.action = action;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}

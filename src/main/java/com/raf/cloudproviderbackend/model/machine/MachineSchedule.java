package com.raf.cloudproviderbackend.model.machine;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "machine_schedules")
public class MachineSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineScheduleId;
    @Column(nullable = false)
    private Date scheduledDate;
    @Column(nullable = false)
    private MachineActionEnum action;
    @Column(nullable = false)
    private boolean sentToExecute;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Machine machine;

    public MachineSchedule() {
    }

    public MachineSchedule(Date scheduledDate, MachineActionEnum action, Machine machine, boolean sentToExecute) {
        this.scheduledDate = scheduledDate;
        this.action = action;
        this.machine = machine;
        this.sentToExecute = sentToExecute;
    }

    public Long getMachineScheduleId() {
        return machineScheduleId;
    }

    public void setMachineScheduleId(Long machineScheduleId) {
        this.machineScheduleId = machineScheduleId;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
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

    public boolean isSentToExecute() {
        return sentToExecute;
    }

    public void setSentToExecute(boolean completed) {
        this.sentToExecute = completed;
    }
}

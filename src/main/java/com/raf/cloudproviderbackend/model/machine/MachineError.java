package com.raf.cloudproviderbackend.model.machine;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "machine_errors")
public class MachineError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineErrorId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date errorDate;
    @Column(nullable = false)
    private MachineActionEnum action;
    @Column(nullable = false)
    private String message;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Machine machine;

    public MachineError() {
    }

    public MachineError(Date errorDate, MachineActionEnum action, String message, Machine machine) {
        this.errorDate = errorDate;
        this.action = action;
        this.message = message;
        this.machine = machine;
    }

    public Long getMachineErrorId() {
        return machineErrorId;
    }

    public void setMachineErrorId(Long machineErrorId) {
        this.machineErrorId = machineErrorId;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}

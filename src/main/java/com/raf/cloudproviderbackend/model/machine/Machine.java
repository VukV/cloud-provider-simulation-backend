package com.raf.cloudproviderbackend.model.machine;

import com.raf.cloudproviderbackend.model.user.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineId;

    @Column(nullable = false)
    private MachineStatusEnum machineStatus;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean occupied;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User createdBy;

    @Column
    @Version
    private Integer version = 0;

    public Machine() {
    }

    public Machine(MachineStatusEnum machineStatus, boolean active, Date createdDate, String name, boolean occupied, User createdBy) {
        this.machineStatus = machineStatus;
        this.active = active;
        this.createdDate = createdDate;
        this.name = name;
        this.occupied = occupied;
        this.createdBy = createdBy;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

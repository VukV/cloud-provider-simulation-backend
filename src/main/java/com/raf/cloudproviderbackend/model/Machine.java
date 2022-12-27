package com.raf.cloudproviderbackend.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean occupied;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    public Machine() {
    }

    public Machine(MachineStatusEnum machineStatus, boolean active, LocalDateTime createdDate, String name, boolean occupied, User createdBy) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
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
}

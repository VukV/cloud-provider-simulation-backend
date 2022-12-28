package com.raf.cloudproviderbackend.model.machine;

import javax.persistence.*;

@Entity
@Table(name = "machine_errors")
public class MachineError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineErrorId;

    //TODO


    public Long getMachineErrorId() {
        return machineErrorId;
    }

    public void setMachineErrorId(Long machineErrorId) {
        this.machineErrorId = machineErrorId;
    }
}

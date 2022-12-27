package com.raf.cloudproviderbackend.service;

import com.raf.cloudproviderbackend.repository.MachineRepository;
import org.springframework.stereotype.Service;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }
}

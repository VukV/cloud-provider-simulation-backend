package com.raf.cloudproviderbackend.mapper;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.model.Machine;
import org.springframework.stereotype.Component;

@Component
public class MachineMapper {

    public MachineDto machineToMachineDto(Machine machine){
        MachineDto machineDto = new MachineDto();

        machineDto.setMachineId(machine.getMachineId());
        machineDto.setMachineStatus(machine.getMachineStatus());
        machineDto.setName(machine.getName());
        machineDto.setCreatedDate(machine.getCreatedDate());

        return machineDto;
    }
}

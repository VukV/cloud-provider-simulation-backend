package com.raf.cloudproviderbackend.mapper;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.dto.machine.MachineErrorDto;
import com.raf.cloudproviderbackend.model.machine.Machine;
import com.raf.cloudproviderbackend.model.machine.MachineError;
import org.springframework.stereotype.Component;

@Component
public class MachineMapper {

    public MachineDto machineToMachineDto(Machine machine){
        MachineDto machineDto = new MachineDto();

        machineDto.setMachineId(machine.getMachineId());
        machineDto.setMachineStatus(machine.getMachineStatus());
        machineDto.setName(machine.getName());
        machineDto.setCreatedDate(machine.getCreatedDate().toInstant().getEpochSecond());

        return machineDto;
    }

    public MachineErrorDto machineErrorToMachineErrorDto(MachineError machineError){
        MachineErrorDto machineErrorDto = new MachineErrorDto();

        machineErrorDto.setMachineErrorId(machineError.getMachineErrorId());
        machineErrorDto.setErrorDate(machineError.getErrorDate().getTime() / 1000);
        machineErrorDto.setAction(machineError.getAction());
        machineErrorDto.setMessage(machineErrorDto.getMessage());
        machineErrorDto.setMachineId(machineError.getMachine().getMachineId());
        machineErrorDto.setMachineName(machineError.getMachine().getName());

        return machineErrorDto;
    }
}

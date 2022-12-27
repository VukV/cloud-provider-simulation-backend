package com.raf.cloudproviderbackend.service;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.exceptions.MachineNotFoundException;
import com.raf.cloudproviderbackend.exceptions.MachineOwnershipException;
import com.raf.cloudproviderbackend.mapper.MachineMapper;
import com.raf.cloudproviderbackend.model.Machine;
import com.raf.cloudproviderbackend.model.MachineStatusEnum;
import com.raf.cloudproviderbackend.model.User;
import com.raf.cloudproviderbackend.repository.MachineRepository;
import com.raf.cloudproviderbackend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final UserRepository userRepository;
    private final MachineMapper machineMapper;

    public MachineService(MachineRepository machineRepository, UserRepository userRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.machineMapper = machineMapper;
    }

    @Transactional
    public MachineDto createMachine(String machineName){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        Machine machine = new Machine(
                MachineStatusEnum.STOPPED,
                false,
                LocalDateTime.now(),
                machineName,
                false,
                user
        );
        machineRepository.save(machine);

        return machineMapper.machineToMachineDto(machine);
    }

    @Transactional
    public void deleteMachine(Long machineId){
        Machine machine = machineRepository.findByMachineId(machineId);

        if(machine != null){
            checkMachineOwner(machine);
            machine.setActive(false);
            return;
        }

        throw new MachineNotFoundException();
    }

    private void checkMachineOwner(Machine machine){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(machine.getCreatedBy().getEmail().equals(email)){
            return;
        }

        throw new MachineOwnershipException();
    }
}

package com.raf.cloudproviderbackend.service;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.dto.machine.MachineScheduleDto;
import com.raf.cloudproviderbackend.exceptions.MachineNotFoundException;
import com.raf.cloudproviderbackend.exceptions.MachineOccupiedException;
import com.raf.cloudproviderbackend.exceptions.MachineOwnershipException;
import com.raf.cloudproviderbackend.exceptions.MachineStatusException;
import com.raf.cloudproviderbackend.mapper.MachineMapper;
import com.raf.cloudproviderbackend.model.machine.Machine;
import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;
import com.raf.cloudproviderbackend.model.machine.MachineSchedule;
import com.raf.cloudproviderbackend.model.machine.MachineStatusEnum;
import com.raf.cloudproviderbackend.model.user.User;
import com.raf.cloudproviderbackend.repository.MachineRepository;
import com.raf.cloudproviderbackend.repository.MachineScheduleRepository;
import com.raf.cloudproviderbackend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineScheduleRepository machineScheduleRepository;
    private final UserRepository userRepository;
    private final MachineMapper machineMapper;

    public MachineService(MachineRepository machineRepository, MachineScheduleRepository machineScheduleRepository, UserRepository userRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.machineScheduleRepository = machineScheduleRepository;
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

    @Transactional
    public void handleMachine(Long machineId, MachineActionEnum machineAction){
        Machine machine = machineRepository.findByMachineId(machineId);

        if(machine != null){
            checkMachineOwner(machine);
            checkActionAndStatus(machineAction, machine.getMachineStatus());
            isMachineOccupied(machine);

            //TODO send to queue
        }

        throw new MachineNotFoundException();
    }

    @Transactional
    public void addScheduledTask(MachineScheduleDto machineScheduleDto, MachineActionEnum machineAction){
        Machine machine = machineRepository.findByMachineId(machineScheduleDto.getMachineId());

        if(machine != null){
            checkMachineOwner(machine);
            //TODO add to schedule repo
        }

        throw new MachineNotFoundException();
    }

    @Scheduled(fixedDelay = 30000)
    private void executeScheduledTasks(){
        List<MachineSchedule> scheduleList = machineScheduleRepository.findAllByScheduledDateBefore(LocalDateTime.now());
        //TODO
    }

    private void checkMachineOwner(Machine machine){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(machine.getCreatedBy().getEmail().equals(email)){
            return;
        }

        throw new MachineOwnershipException();
    }

    private void checkActionAndStatus(MachineActionEnum action, MachineStatusEnum status){
        switch (action){
            case START:
                if(status != MachineStatusEnum.STOPPED){
                    throw new MachineStatusException("Machine is not stopped.");
                }
                break;
            case STOP:
            case RESTART:
                if(status != MachineStatusEnum.RUNNING){
                    throw new MachineStatusException("Machine is not running.");
                }
                break;
            default:
                throw new MachineStatusException("Invalid action.");
        }
    }

    private void isMachineOccupied(Machine machine){
        if(machine.isOccupied()){
            throw new MachineOccupiedException();
        }
    }
}

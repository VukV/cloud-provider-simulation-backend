package com.raf.cloudproviderbackend.service;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.dto.machine.MachineScheduleDto;
import com.raf.cloudproviderbackend.exceptions.MachineNotFoundException;
import com.raf.cloudproviderbackend.exceptions.MachineOccupiedException;
import com.raf.cloudproviderbackend.exceptions.MachineOwnershipException;
import com.raf.cloudproviderbackend.exceptions.MachineStatusException;
import com.raf.cloudproviderbackend.mapper.MachineMapper;
import com.raf.cloudproviderbackend.model.machine.*;
import com.raf.cloudproviderbackend.model.user.User;
import com.raf.cloudproviderbackend.repository.MachineErrorRepository;
import com.raf.cloudproviderbackend.repository.MachineRepository;
import com.raf.cloudproviderbackend.repository.MachineScheduleRepository;
import com.raf.cloudproviderbackend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineScheduleRepository machineScheduleRepository;
    private final MachineErrorRepository machineErrorRepository;
    private final UserRepository userRepository;
    private final MachineMapper machineMapper;

    public MachineService(MachineRepository machineRepository, MachineScheduleRepository machineScheduleRepository, MachineErrorRepository machineErrorRepository, UserRepository userRepository, MachineMapper machineMapper) {
        this.machineRepository = machineRepository;
        this.machineScheduleRepository = machineScheduleRepository;
        this.machineErrorRepository = machineErrorRepository;
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
                Date.from(Instant.now()),
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
            if(machine.getMachineStatus() == MachineStatusEnum.STOPPED){
                machine.setActive(false);
                return;
            }
            throw new MachineStatusException("Machine is not stopped.");
        }

        throw new MachineNotFoundException();
    }

    @Transactional
    public void handleMachine(Long machineId, MachineActionEnum machineAction){
        Machine machine = machineRepository.findByMachineId(machineId);

        if(machine != null){
            checkMachineOwner(machine);
            checkAndSetMachineOccupied(machine);
            checkActionAndStatus(machineAction, machine.getMachineStatus());

            //TODO send to queue
            return;
        }

        throw new MachineNotFoundException();
    }

    @Transactional
    public void addScheduledTask(MachineScheduleDto machineScheduleDto, MachineActionEnum machineAction){
        Machine machine = machineRepository.findByMachineId(machineScheduleDto.getMachineId());

        if(machine != null){
            checkMachineOwner(machine);
            Date scheduleDate = Date.from(Instant.ofEpochSecond(machineScheduleDto.getScheduleDate()));
            MachineSchedule newSchedule = new MachineSchedule(scheduleDate, machineAction, machine, false);
            machineScheduleRepository.save(newSchedule);
            return;
        }

        throw new MachineNotFoundException();
    }

    @Scheduled(fixedDelay = 30000)
    @Transactional(dontRollbackOn = {MachineOccupiedException.class, MachineStatusException.class})
    public void executeScheduledTasks(){
        System.out.println("usao baco");
        List<MachineSchedule> scheduleList = machineScheduleRepository.findAllByScheduledDateBeforeAndSentToExecute(Date.from(Instant.now()), false);
        if(scheduleList.isEmpty()){
            return;
        }

        for(MachineSchedule scheduledTask: scheduleList){
            try {
                checkActionAndStatus(scheduledTask.getAction(), scheduledTask.getMachine().getMachineStatus());
                checkAndSetMachineOccupied(scheduledTask.getMachine());

                //TODO send to queue
                System.out.println("usao baco unutri");
                scheduledTask.setSentToExecute(true);
            }
            catch (MachineOccupiedException moe){
                logError(scheduledTask, "Machine already occupied.");
            }
            catch (MachineStatusException mse){
                logError(scheduledTask, "Wrong machine action.");
            }
        }
    }

    private void logError(MachineSchedule machineSchedule, String message){
        MachineError error = new MachineError(Date.from(Instant.now()), machineSchedule.getAction(), message, machineSchedule.getMachine());
        machineErrorRepository.save(error);
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

    private void checkAndSetMachineOccupied(Machine machine){
        if(machine.isOccupied()){
            throw new MachineOccupiedException();
        }

        machine.setOccupied(true);
    }
}

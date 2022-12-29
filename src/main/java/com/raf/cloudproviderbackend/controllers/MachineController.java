package com.raf.cloudproviderbackend.controllers;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.dto.machine.MachineErrorDto;
import com.raf.cloudproviderbackend.dto.machine.MachineScheduleDto;
import com.raf.cloudproviderbackend.model.machine.MachineActionEnum;
import com.raf.cloudproviderbackend.model.machine.MachineError;
import com.raf.cloudproviderbackend.model.machine.MachineStatusEnum;
import com.raf.cloudproviderbackend.model.user.RoleEnum;
import com.raf.cloudproviderbackend.security.CheckRole;
import com.raf.cloudproviderbackend.service.MachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    @CheckRole(roles = RoleEnum.SEARCH_MACHINES)
    public ResponseEntity<List<MachineDto>> getMachines(@RequestParam(required = false) String machineName, @RequestParam(required = false)List<MachineStatusEnum> statusList, @RequestParam(required = false) Long dateFrom, @RequestParam(required = false) Long dateTo){
        return ResponseEntity.ok(machineService.getMachines(machineName, statusList, dateFrom, dateTo));
    }

    @PostMapping
    @CheckRole(roles = RoleEnum.CREATE_MACHINES)
    public ResponseEntity<MachineDto> createMachine(@RequestParam String machineName){
        return ResponseEntity.ok(machineService.createMachine(machineName));
    }

    @DeleteMapping("/{machineId}")
    @CheckRole(roles = RoleEnum.DESTROY_MACHINES)
    public ResponseEntity<?> deleteMachine(@PathVariable Long machineId){
        machineService.deleteMachine(machineId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/start/{machineId}")
    @CheckRole(roles = RoleEnum.START_MACHINES)
    public ResponseEntity<?> startMachine(@PathVariable Long machineId){
        machineService.handleMachine(machineId, MachineActionEnum.START);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/stop/{machineId}")
    @CheckRole(roles = RoleEnum.STOP_MACHINES)
    public ResponseEntity<?> stopMachine(@PathVariable Long machineId){
        machineService.handleMachine(machineId, MachineActionEnum.STOP);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/restart/{machineId}")
    @CheckRole(roles = RoleEnum.RESTART_MACHINES)
    public ResponseEntity<?> restartMachine(@PathVariable Long machineId){
        machineService.handleMachine(machineId, MachineActionEnum.RESTART);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/schedule/start")
    @CheckRole(roles = RoleEnum.START_MACHINES)
    public ResponseEntity<?> scheduleStart(@Valid @RequestBody MachineScheduleDto machineScheduleDto){
        machineService.addScheduledTask(machineScheduleDto, MachineActionEnum.START);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/schedule/stop")
    @CheckRole(roles = RoleEnum.STOP_MACHINES)
    public ResponseEntity<?> scheduleStop(@Valid @RequestBody MachineScheduleDto machineScheduleDto){
        machineService.addScheduledTask(machineScheduleDto, MachineActionEnum.STOP);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/schedule/restart")
    @CheckRole(roles = RoleEnum.RESTART_MACHINES)
    public ResponseEntity<?> scheduleRestart(@Valid @RequestBody MachineScheduleDto machineScheduleDto){
        machineService.addScheduledTask(machineScheduleDto, MachineActionEnum.RESTART);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/errors")
    public ResponseEntity<List<MachineErrorDto>> getMachineErrors(){
        return ResponseEntity.ok(machineService.getMachineErrors());
    }
}

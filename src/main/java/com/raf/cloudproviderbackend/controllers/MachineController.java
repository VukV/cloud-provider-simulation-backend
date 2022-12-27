package com.raf.cloudproviderbackend.controllers;

import com.raf.cloudproviderbackend.dto.machine.MachineDto;
import com.raf.cloudproviderbackend.model.RoleEnum;
import com.raf.cloudproviderbackend.security.CheckRole;
import com.raf.cloudproviderbackend.service.MachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
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
}

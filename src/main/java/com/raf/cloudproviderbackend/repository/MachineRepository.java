package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.machine.Machine;
import com.raf.cloudproviderbackend.model.machine.MachineStatusEnum;
import com.raf.cloudproviderbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findByMachineId(Long machineId);
    @Query("select m from Machine m where m.createdBy.userId = :userId")
    List<Machine> findAllByUserId(Long userId);
    List<Machine> findAllByCreatedByAndNameContains(User user, String machineName);
    List<Machine> findAllByCreatedByAndMachineStatus(User user, MachineStatusEnum machineStatus);
    List<Machine> findAllByCreatedDateBetween(LocalDateTime firstDate, LocalDateTime secondDate);



}

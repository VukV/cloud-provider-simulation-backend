package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.Machine;
import com.raf.cloudproviderbackend.model.MachineStatusEnum;
import com.raf.cloudproviderbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findMachineByMachineId(Long machineId);
    @Query("select m from Machine m where m.createdBy.userId = :userId")
    List<Machine> findAllByUserId(Long userId);
    List<Machine> findAllByCreatedByAndNameContains(User user, String machineName);
    List<Machine> findAllByCreatedByAndMachineStatus(User user, MachineStatusEnum machineStatus);
    List<Machine> findAllByCreatedDateBetween(LocalDateTime firstDate, LocalDateTime secondDate);



}

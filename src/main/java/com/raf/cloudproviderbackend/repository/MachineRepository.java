package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.machine.Machine;
import com.raf.cloudproviderbackend.model.machine.MachineStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findByMachineId(Long machineId);

    @Query("select m from Machine m where m.createdBy.email = :email " +
            "and (:machineName is null or m.name like %:machineName%) " +
            "and ((:statusList) is null  or m.machineStatus in (:statusList)) " +
            "and (cast(:dateFrom as date) is null or m.createdDate >= :dateFrom) " +
            "and (cast(:dateTo as date) is null or m.createdDate <= :dateTo) " +
            "and m.active = true ")
    List<Machine> findAllMachines(String email, String machineName, List<MachineStatusEnum> statusList, Date dateFrom, Date dateTo);
}

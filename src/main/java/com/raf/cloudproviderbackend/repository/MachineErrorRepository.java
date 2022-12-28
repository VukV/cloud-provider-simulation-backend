package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.machine.MachineError;
import com.raf.cloudproviderbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineErrorRepository extends JpaRepository<MachineError, Long> {

    MachineError getByMachineErrorId(Long id);
    List<MachineError> getAllByMachine_CreatedBy(User user);
}

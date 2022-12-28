package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.machine.MachineSchedule;
import com.raf.cloudproviderbackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MachineScheduleRepository extends JpaRepository<MachineSchedule, Long> {

    List<MachineSchedule> findAllByScheduledDateBefore(LocalDateTime now);
}

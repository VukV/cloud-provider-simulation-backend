package com.raf.cloudproviderbackend.repository;

import com.raf.cloudproviderbackend.model.machine.MachineSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface MachineScheduleRepository extends JpaRepository<MachineSchedule, Long> {

    List<MachineSchedule> findAllByScheduledDateBeforeAndSentToExecute(Date now, boolean sent);
}

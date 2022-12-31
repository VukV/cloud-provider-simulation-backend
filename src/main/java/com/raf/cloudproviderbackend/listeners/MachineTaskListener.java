package com.raf.cloudproviderbackend.listeners;

import com.raf.cloudproviderbackend.dto.machine.MachineQueueDto;
import com.raf.cloudproviderbackend.exceptions.MachineOccupiedException;
import com.raf.cloudproviderbackend.model.machine.Machine;
import com.raf.cloudproviderbackend.model.machine.MachineStatusEnum;
import com.raf.cloudproviderbackend.repository.MachineRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Component
public class MachineTaskListener {

    private final MachineRepository machineRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MachineTaskListener(MachineRepository machineRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.machineRepository = machineRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "machineTaskQueue", concurrency = "5")
    public void executeActionFromQueue(Message message) throws IOException, ClassNotFoundException, InterruptedException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        MachineQueueDto machineQueueDto = (MachineQueueDto) objectInputStream.readObject();
        executeAction(machineQueueDto);
    }

    private void executeAction(MachineQueueDto machineQueueDto) throws InterruptedException {
        switch (machineQueueDto.getAction()){
            case STOP:
                startStop(machineQueueDto, MachineStatusEnum.STOPPED);
                break;
            case START:
                startStop(machineQueueDto, MachineStatusEnum.RUNNING);
                break;
            case RESTART:
                restart(machineQueueDto);
                break;
            default:
                break;
        }
    }

    @Transactional
    public void startStop(MachineQueueDto machineQueueDto, MachineStatusEnum status) throws InterruptedException {
        Machine machine = machineRepository.findByMachineId(machineQueueDto.getMachineId());
        if(machine != null){
            Thread.sleep(10000);
            machine.setMachineStatus(status);
            try {
                machine.setActive(true);
            }
            catch (ObjectOptimisticLockingFailureException e){
                throw new MachineOccupiedException();
            }

            simpMessagingTemplate.convertAndSend("/topic/" + machineQueueDto.getUserEmail(), machine);
        }
    }

    @Transactional
    public void restart(MachineQueueDto machineQueueDto) throws InterruptedException {
        Machine machine = machineRepository.findByMachineId(machineQueueDto.getMachineId());
        if(machine != null){
            Thread.sleep(5000);
            machine.setMachineStatus(MachineStatusEnum.STOPPED);
            simpMessagingTemplate.convertAndSend("/topic/" + machineQueueDto.getUserEmail(), machine);

            Thread.sleep(5000);
            machine.setMachineStatus(MachineStatusEnum.RUNNING);

            try {
                machine.setActive(true);
            }
            catch (ObjectOptimisticLockingFailureException e){
                throw new MachineOccupiedException();
            }

            simpMessagingTemplate.convertAndSend("/topic/" + machineQueueDto.getUserEmail(), machine);
        }
    }
}

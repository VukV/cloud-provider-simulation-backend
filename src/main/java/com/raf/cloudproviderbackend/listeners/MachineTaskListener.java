package com.raf.cloudproviderbackend.listeners;

import com.raf.cloudproviderbackend.dto.machine.MachineQueueDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Component
public class MachineTaskListener {

    @RabbitListener(queues = "machineTaskQueue")
    public void executeActionFromQueue(Message message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getBody());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        MachineQueueDto machineQueueDto = (MachineQueueDto) objectInputStream.readObject();
        executeAction(machineQueueDto);
    }

    private void executeAction(MachineQueueDto machineQueueDto){
        //todo execute
        //todo send via socket
    }
}

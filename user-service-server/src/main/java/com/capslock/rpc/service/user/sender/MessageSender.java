package com.capslock.rpc.service.user.sender;

import com.capslock.commons.model.ClientInfo;
import com.capslock.commons.mq.MessageQueuePublisher;
import com.capslock.commons.packet.cluster.EnvelopeClusterPacket;
import com.capslock.commons.packet.cluster.EnvelopePacketType;
import com.capslock.commons.packet.protocol.ChangedDataNotificationProtocol;
import com.capslock.commons.packet.socketOutboundPacket.SocketOutboundChangedDataNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by alvin.
 */
@Component
public class MessageSender {
    @Autowired
    private MessageQueuePublisher messageQueuePublisher;
    @Autowired
    private ObjectMapper objectMapper;
    private BlockingQueue<RetryEntry> retryEntryQueue = new ArrayBlockingQueue<>(1000);
    private RetryRunner retryRunner = new RetryRunner(retryEntryQueue);

    public void sendDataChangedNotification(final long userId, final long sequence, final ChangedDataNotificationProtocol.Type type) {
        final SocketOutboundChangedDataNotification notification =
                new SocketOutboundChangedDataNotification(sequence, type.getValue());
        final ClientInfo clientInfo = new ClientInfo(userId);
        final EnvelopeClusterPacket envelopeClusterPacket = new EnvelopeClusterPacket(clientInfo, notification, EnvelopePacketType.S2C);
        try {
            messageQueuePublisher.publish(objectMapper.writeValueAsBytes(envelopeClusterPacket));
        } catch (IOException e) {
            retryEntryQueue.offer(new RetryEntry(envelopeClusterPacket));
            e.printStackTrace();
        }
    }

    @Data
    public static class RetryEntry {
        private final EnvelopeClusterPacket envelopeClusterPacket;
        private int times = 0;

        public void addTimes() {
            times += 1;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public class RetryRunner extends Thread {
        private final BlockingQueue<RetryEntry> retryEntryQueue;

        @Override
        public void run() {
            RetryEntry retryEntry;
            while (true) {
                try {
                    retryEntry = retryEntryQueue.take();
                    final EnvelopeClusterPacket packet = retryEntry.getEnvelopeClusterPacket();
                    try {
                        messageQueuePublisher.publish(objectMapper.writeValueAsBytes(packet));
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (retryEntry.getTimes() < 3) {
                            retryEntry.addTimes();
                            retryEntryQueue.offer(retryEntry);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

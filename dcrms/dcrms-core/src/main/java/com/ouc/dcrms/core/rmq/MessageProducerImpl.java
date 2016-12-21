package com.ouc.dcrms.core.rmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WuPing
 * @version 2016年11月28日 下午9:55:15
 */

@Service
public class MessageProducerImpl implements MessageProducer {
    
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.ouc.dcrms.core.rmq;

/**
 * @author WuPing
 * @version 2016年11月28日 下午9:54:18
 */

public interface MessageProducer {
    //发送消息到指定队列
    public void sendDataToQueue(String queueKey, Object object);
}

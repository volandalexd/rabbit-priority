package com.example.rabbitpriority.service;

import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ListenerContainerIdleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
@Log
public class RabbitMqListener {
    Random random = new Random();

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "query-example-1")
    public void worker1(String message) throws InterruptedException {
        log.info("query 1 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

    @RabbitListener(queues = "query-example-2")
    public void worker2(String message) throws InterruptedException {
        log.info("query 2 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

    @EventListener
    public void notifyQueueIsEmpty(ListenerContainerIdleEvent events) {
        Arrays.stream(events.getQueueNames()).forEach(log::info);
    }


}
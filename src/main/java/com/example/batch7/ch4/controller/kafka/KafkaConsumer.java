package com.example.batch7.ch4.controller.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics="my_topic", groupId="my_group_id")
    public void getMessage(String message){
        System.out.println("====");
        System.out.println("Pesan="+message);
        System.out.println("Group ID = "+KafkaUtils.getConsumerGroupId());
        // user database kirim
        // firebase -> notif settingan
        //
    }
}


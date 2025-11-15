package com.selfproject.journalAPP.service;

import com.selfproject.journalAPP.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    // use a property value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    // we give directly class out their string is their
   // @KafkaListener(topics = "journal-group",groupId = "journal-group")
    public void consumer(SentimentData sentimentData){
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData)
    {
        emailService.sendMail(sentimentData.getEmail(),"Sentiment for previous week ", sentimentData.getSentiment());
    }
}

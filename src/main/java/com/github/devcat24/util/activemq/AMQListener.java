package com.github.devcat24.util.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class AMQListener {

    @JmsListener(destination = "amq.exam.queue01")
    public void receiveMessage(final Message msg) throws JMSException {
        System.out.println("Received message: " + msg.getPayload());

        /*
        String response = null;
         if(msg instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)msg;
            String messageData = textMessage.getText();

            //Map map = new Gson().fromJson(messageData, Map.class);
            //response  = "Hello " + map.get("name");

            response = messageData;
            System.out.println("Received Msg: " + messageData);
        }
        return response;
        */

    }
}

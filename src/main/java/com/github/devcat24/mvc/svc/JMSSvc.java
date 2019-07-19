package com.github.devcat24.mvc.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("JMSSvc")
public class JMSSvc {
    private JmsTemplate jmsTemplate;
    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) { this.jmsTemplate = jmsTemplate; }


    @Transactional
    public boolean sendAmqQueue01()  {
        String qName = "amq.exam.queue01";
        //jmsTemplate.setPubSubDomain(true);  // -> publish as topic
        try {
            jmsTemplate.convertAndSend(qName, "New Message !");
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}

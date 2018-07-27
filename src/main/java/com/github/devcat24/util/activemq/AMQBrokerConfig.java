/* Configuration for embedded ActiveMQ */
package com.github.devcat24.util.activemq;


import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*

@Configuration
public class AMQBrokerConfig {
    @Bean
    public BrokerService broker() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:51515");
        // broker.addConnector("vm://localhost");

        // broker.setUseJmx(true);
        // ManagementContext managementContext = new ManagementContext();
        // managementContext.setConnectorPort(1099);
        // broker.setManagementContext(managementContext);


        broker.setPersistent(false);
        return broker;
    }
}
*/

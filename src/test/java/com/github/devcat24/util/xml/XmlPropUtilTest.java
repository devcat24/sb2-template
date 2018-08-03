package com.github.devcat24.util.xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class XmlPropUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(XmlPropUtilTest.class);

    private XmlPropUtil xmlPropUtil;
    @Autowired
    void setXmlPropUtil(XmlPropUtil xmlPropUtil){
        this.xmlPropUtil = xmlPropUtil;
    }

    @Test
    public void streamSample01Test() throws Exception {
        // Initialise on Application startup(@PostConstruct) in actual implementation & reload !
        xmlPropUtil.readSQLText();

        logger.info("-------------------------------------");
        logger.info(xmlPropUtil.getSqlById("getUserDefinedSQL01"));
        logger.info("-------------------------------------");
        logger.info(xmlPropUtil.getSqlById("getUserDefinedSQL02"));
        logger.info("-------------------------------------");

    }



}

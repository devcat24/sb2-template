package com.github.devcat24.util.xml;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Data
@Component("XmlPropUtil")
public class XmlPropUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlPropUtil.class);

    @Getter
    @Setter
    private static String sqlText;

    @Value("${xml.sql.file}")
    private String xmlSqlFile;

    private ResourceLoader resourceLoader;
    @Autowired
    void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // Initialise on Application startup(@PostConstruct) in actual implementation & reload !
    public void readSQLText() throws IOException {
        Resource resource = new ClassPathResource(xmlSqlFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        XmlPropUtil.sqlText = reader.lines().collect(Collectors.joining("\n"));
    }

    public String getSqlById(String id)  {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(new ByteArrayInputStream(sqlText.getBytes()));
            doc.getDocumentElement().normalize();

            //logger.info("Root Element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("sql");
            for (int ins = 0; ins < nodeList.getLength(); ins++) {
                Node node = nodeList.item(ins);
                String tagId = ((Element) node).getAttribute("id");
                if (StringUtils.equalsIgnoreCase(id, tagId)) {
                    return ((Element) node).getElementsByTagName("query-string").item(0).getTextContent();
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex){
            ex.printStackTrace();
            return "Exception while parsing SQL defined xml file";
        }
        return "No matching SQL for id: " + id;
    }

}

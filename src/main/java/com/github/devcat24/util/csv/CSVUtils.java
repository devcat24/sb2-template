package com.github.devcat24.util.csv;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.StringReader;
import java.util.List;

public class CSVUtils {

    public List<CSVTestDTO> csvToJavaObj() throws Exception{
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(CSVTestDTO.class);
        String[] columns = new String[] {"id", "name", "order"};
        strategy.setColumnMapping(columns);

        File srcFile = new File("/home/tomcat/test01.csv");
        StringReader reader = new StringReader(FileUtils.readFileToString(srcFile, "UTF-8"));
        CsvToBean csv = new CsvToBean();
        List<CSVTestDTO> list = csv.parse(strategy, reader);
        for(CSVTestDTO order:list){
            System.out.println("id:" + order.getId() + "   name: "+ order.getName() + "    order: "+ order.getOrder());
        }

        return list;
    }

    public void javaObjToCSV() throws Exception {
        List<CSVTestDTO> list = csvToJavaObj();

        ObjectMapper mapper = new ObjectMapper();
        CsvMapper csvMapper = new CsvMapper();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        System.out.println("-------- json string -----------");
        System.out.println(json);

        System.out.println("-------- csv string -----------");
        CsvSchema schema = csvMapper.schemaFor(CSVTestDTO.class).withHeader();
        System.out.println(csvMapper.writer(schema).writeValueAsString(list));
    }
}

package com.github.devcat24.util.csv;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
@Slf4j
public class CSVUtils {

    @SuppressWarnings("unchecked")
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


    public <T> String objToCSV(List<T> objects, List<String> headerLists){
        return objToCSV(objects, headerLists, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER);
    }


    @SuppressWarnings("unchecked")
    public <T> String objToCSV(List<T> objects, List<String> headerLists, char csvSeparator, char csvQuoteChar, char csvEscapeChar) {
        /*char csvSeparator = '\t';  // CSVWriter.DEFAULT_SEPARATOR
        char csvQuoteChar = '"';   // CSVWriter.DEFAULT_QUOTE_CHARACTER
        char csvEscapeChar = '\\';*/
        String rtn = null;

        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = null;

        try {
            if(headerLists != null && headerLists.size() > 0) {
                String[] headers = headerLists.toArray(new String[headerLists.size()]);
                csvWriter = new CSVWriter(writer, csvSeparator, csvQuoteChar, csvEscapeChar);
                csvWriter.writeNext(headers);
                csvWriter.flush();
            }

            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(csvSeparator)
                    .withQuotechar(csvQuoteChar)
                    .withEscapechar(csvEscapeChar)
                    .build();

            beanToCsv.write(objects);
            writer.flush();

            rtn = writer.toString();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException ex) {
            log.error("Exception - CSVUtils.objToCSV() :" + ex.toString());
        } finally{
            if(csvWriter != null) { try{ csvWriter.close(); } catch(Exception e) {e.printStackTrace();}}
            try{ writer.close(); } catch(Exception e) {e.printStackTrace();}
        }

        return rtn;
    }

}

package com.github.devcat24.util.csv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//@TestPropertySource(properties = {"fetch.size=5"}) // directly, overrides values in 'application.properties'
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class CSVUtilTest {


    @Test
    public void testCSVUtil() throws Exception {
        List<CSVTestDTO> list = new ArrayList<>();
        CSVTestDTO obj01 = CSVTestDTO.builder().id(1001L).name("Dave").order("Book").build();
        CSVTestDTO obj02 = CSVTestDTO.builder().id(1002L).name("Steven").order("Tablet").build();
        CSVTestDTO obj03 = CSVTestDTO.builder().id(1003L).name("James").order("Laptop").build();

        list.add(obj01);
        list.add(obj02);
        list.add(obj03);

        String[] headers = {"id", "name", "order"};

        System.out.println(new CSVUtils().objToCSV(list, Arrays.asList(headers)));




    }
}

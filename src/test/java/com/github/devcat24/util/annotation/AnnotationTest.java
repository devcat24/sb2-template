package com.github.devcat24.util.annotation;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;


//@TestPropertySource(properties = {"fetch.size=5"}) // directly, overrides values in 'application.properties'
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class AnnotationTest {


    @Test
    public void testAnnotationUtil() throws Exception {
        Field[] fields = FieldUtils.getAllFields(AnnotationTestBean.class);
        for(Field field : fields) {
            System.out.println("Field Name: " + field.getName());

            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                System.out.println("   > " + annotation.toString());
            }


        }


/*        List<CSVTestDTO> list = new ArrayList<>();
        CSVTestDTO obj01 = CSVTestDTO.builder().id(1001L).name("Dave").order("Book").build();
        CSVTestDTO obj02 = CSVTestDTO.builder().id(1002L).name("Steven").order("Tablet").build();
        CSVTestDTO obj03 = CSVTestDTO.builder().id(1003L).name("James").order("Laptop").build();

        list.add(obj01);
        list.add(obj02);
        list.add(obj03);

        String [] headers = {"id", "name", "order"};

        System.out.println(new CSVUtils().objToCSV(list, Arrays.asList(headers)));*/




    }
}

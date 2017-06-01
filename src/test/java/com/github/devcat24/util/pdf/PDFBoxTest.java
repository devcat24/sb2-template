package com.github.devcat24.util.pdf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//@TestPropertySource(properties = {"fetch.size=5"}) // directly, overrides values in 'application.properties'
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class PDFBoxTest {


    @Test
    public void testRestTemplate() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = classLoader.getResource("tmp_test_files/test_doc_01.pdf").getFile();

        PDFBoxUtil pdfBoxUtil = new PDFBoxUtil();
        String pdfText = pdfBoxUtil.readFromPDF(filePath);
        //String pdfText = pdfBoxUtil.readFromPDF("resources/tmp_test_files/test_doc_01.pdf");
        System.out.println(pdfText);


    }
}

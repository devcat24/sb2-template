package com.github.devcat24.util.pdf;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;


//@TestPropertySource(properties = {"fetch.size=5"}) // directly, overrides values in 'application.properties'
@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class PDFBoxTest {
    // @Autowired
    // ResourceLoader resourceLoader;


    @SuppressWarnings("RedundantThrows")
    @Test
    public void testRestTemplate() throws Exception {
        try {
            // ClassLoader classLoader = getClass().getClassLoader();
            // System.out.println(">>>>>>>>>>>> " + classLoader.getResource("classpath:/application.properties"));
            // System.out.println(">>>>>>>>>>>> " + resourceLoader.getResource("classpath:/application.properties").getFile().getAbsolutePath());

            File file = ResourceUtils.getFile("classpath:tmp_test_files/test_doc_01.pdf");
            String filePath = file.getAbsolutePath();

            PDFBoxUtil pdfBoxUtil = new PDFBoxUtil();
            String pdfText = pdfBoxUtil.readFromPDF(filePath);
            //String pdfText = pdfBoxUtil.readFromPDF("resources/tmp_test_files/test_doc_01.pdf");
            System.out.println(pdfText);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

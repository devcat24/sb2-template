package com.github.devcat24.util.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class ImageResizerTest {

    @Test
    public void testImageResizer() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./static/images/spring_logo.jpg").getFile());

        String srcFilePath = file.getAbsolutePath();
        //String targetFilePath = "/home/tomcat/Downloads/spring_logo_300.jpg";
        String targetFilePath = "target/spring_logo_300.jpg";

        ImageResizer resizer = new ImageResizer();
        resizer.resize(srcFilePath, targetFilePath, 300);

        double originalFileSize = file.length()/1024;
        double compressedFileSize = (new File(targetFilePath)).length()/1024;
        assertTrue("Compressed image (" +compressedFileSize+ ") is smaller than original (" + originalFileSize + "kb)", originalFileSize > compressedFileSize);


    }
}

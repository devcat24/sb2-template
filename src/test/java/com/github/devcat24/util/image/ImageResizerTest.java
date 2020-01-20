package com.github.devcat24.util.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class ImageResizerTest {

    @Test
    public void testImageResizer() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./static/images/spring_logo.jpg").getFile());
        String srcFilePath = file.getAbsolutePath();
        String targetFilePath = "/home/tomcat/Downloads/spring_logo_300.jpg";

        ImageResizer resizer = new ImageResizer();
        resizer.resize(srcFilePath, targetFilePath, 300);


    }
}

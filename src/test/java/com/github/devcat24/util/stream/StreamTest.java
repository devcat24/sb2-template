package com.github.devcat24.util.stream;

import com.github.devcat24.util.collection.ValueComparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class StreamTest {


    @Test
    public void streamSample01Test() throws Exception {
        StreamSample01 sample01 = new StreamSample01();
        sample01.streamSample01();
    }

    @Test
    public void streamSample02Test() throws Exception {
        StreamSample01 sample02 = new StreamSample01();
        sample02.streamSample02();
    }

    @Test
    public void streamSample02aTest() throws Exception {
        StreamSample02 streamSample02 = new StreamSample02();
        streamSample02.steamSample02a();
    }


}

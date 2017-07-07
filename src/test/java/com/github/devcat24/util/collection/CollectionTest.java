package com.github.devcat24.util.collection;

import com.github.devcat24.util.annotation.AnnotationTestBean;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class CollectionTest {


    @Test
    public void testSortedMapByKey() throws Exception {
        Map<Integer, String> random = new HashMap<Integer, String>();
        for (int i = 0; i < 10; i++) {
            random.put((int)(Math.random() * 100), String.valueOf((int) (Math.random() * 100)));
        }

        System.out.println("Initial Map: " + Arrays.toString(random.entrySet().toArray()));
        TreeMap<Integer, String> sorted = new TreeMap<Integer, String>(random);
        System.out.println("Sorted Map: " + Arrays.toString(sorted.entrySet().toArray()));
    }
    @Test
    public void testReverseSortedMapByKey() throws Exception {
        Map<Integer, String> random = new HashMap<Integer, String>();
        for (int i = 0; i < 10; i++) {
            random.put((int)(Math.random() * 100), String.valueOf((int) (Math.random() * 100)));
        }

        System.out.println("Initial Map: " + Arrays.toString(random.entrySet().toArray()));

        TreeMap<Integer, String> sorted = new TreeMap<Integer, String>((o1, o2) -> o2.compareTo(o1));
        sorted.putAll(random);
        System.out.println("Sorted Map: " + Arrays.toString(sorted.entrySet().toArray()));
    }
    @Test
    public void testSortedMapByValue() throws Exception {
        Map<Integer, String> random = new HashMap<Integer, String>();
        for (int i = 0; i < 10; i++) {
            random.put((int)(Math.random() * 100), String.valueOf((int) (Math.random() * 100)));
        }

        System.out.println("Initial Map: " + Arrays.toString(random.entrySet().toArray()));

        TreeMap<Integer, String> sorted = new TreeMap<Integer, String>(new ValueComparator(random));
        sorted.putAll(random);
        System.out.println("Sorted Map: " + Arrays.toString(sorted.entrySet().toArray()));

    }


}

package com.github.devcat24.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class ConcurrentTest {

    @Test
    public void futureExecutor01Test() throws Exception {
        FutureExecutor01 adder = new FutureExecutor01();
        int pSum = adder.parallelSum();
        int sSum = adder.sequentialSum();

        System.out.println("ParallelSum: " + pSum + ", SequentialSum: " + sSum);
        assertEquals(pSum, sSum);
    }
}

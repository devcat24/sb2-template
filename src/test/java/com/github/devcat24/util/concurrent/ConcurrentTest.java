package com.github.devcat24.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;
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

    @Test
    public void completableFutureTest() throws Exception {

        // case #1
        CompletableFuture cFuture01 =
                CompletableFuture.supplyAsync(() -> { return "Running Completable future"; })
                                 //.exceptionally(this::notify)
                                 //.thenApply(this::sendMsg)
                                 .thenAccept(System.out::println);
                                 //.thenCompose(CompletableFuture.supplyAsync(() -> other.getReceiver()))

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture cFuture02 = CompletableFuture.supplyAsync(() -> {
            return "Running completable future";
        }, executorService);


        // case #2
        DeliveryStore store = new DeliveryStore("Toy");
        Future<String> future = store.orderAsync("Lego");

        try{
            System.out.println("Result: " + future.get(10, TimeUnit.SECONDS));
        }   catch(ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        List<DeliveryStore> storeList = Arrays.asList(
                new DeliveryStore("Toy"),
                new DeliveryStore("Cloth"),
                new DeliveryStore("Convenient")
        );

        storeList.stream()
                .map( s -> s.order("Gift package 1"))
                .forEach(System.out::println);

        // Max 30 threads
        ExecutorService excutor = Executors.newFixedThreadPool(Math.min(storeList.size(), 30), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);  // set 'DamonThread' to prevent application shutdown
            return t;
        });

        List<CompletableFuture<String>> futures = storeList.stream()
                .map(ds -> CompletableFuture.supplyAsync(() -> ds.order("Gift package 2"), excutor))
                .collect(toList());

        List<String> result = futures.stream()
                .map(CompletableFuture::join)
                .collect(toList());

        result.stream().forEach(System.out::println);

    }
}

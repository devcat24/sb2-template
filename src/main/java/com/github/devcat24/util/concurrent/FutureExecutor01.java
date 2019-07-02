package com.github.devcat24.util.concurrent;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExecutor01 {
    private int loopCount = 10;
    public Integer parallelSum() {
        long t1 = (new java.util.Date()).getTime();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        int prevInt = 0;
        for(int ins=0 ; ins < loopCount; ins++) {
            if(ins % 2 != 0) {
                Future<Integer> future = executor.submit(new Callable01(prevInt, ins));
                //Future<Integer> future = executor.submit(() -> {
                //    Thread.sleep(10);
                //    return 0;
                //});
                list.add(future);
            }
            prevInt = ins;
        }

        int sum = 0;
        for (Future<Integer> fut : list) {
            try {
                sum = sum + fut.get();
                //sum = sum + fut.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException e) {
            //} catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        long t2 = (new java.util.Date()).getTime();
        System.out.println("Total Sum is " + sum + " (Time taken: " + (t2-t1) + ")");
        return sum;
    }

    public int sequentialSum() {
        long t1 = (new java.util.Date()).getTime();
        Integer totsum = 0;
        for (int i = 0; i < loopCount; i++) {
            totsum = totsum + i;
        }
        long t2 = (new java.util.Date()).getTime();
        System.out.println("sequentialSum Total Sum is " + totsum + " (Time taken: " + (t2-t1) + ")");
        return totsum;
    }

    /*public static void main(String[] args) {
        FutureExecutor01 adder = new FutureExecutor01();
        int pSum = adder.parallelSum();
        int sSum = adder.sequentialSum();

        System.out.println("ParallelSum: " + pSum + ", SequentialSum: " + sSum);
    }*/
}
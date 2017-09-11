package com.github.devcat24.util.concurrent;

import java.util.concurrent.Callable;

// java.util.concurrent.Callable
//     -> extended "Runnable" version with returning 'Generic' type return
//     -> invoke as
//        '   ExecutorService executor = Executors.newFixedThreadPool(10);              '
//        '   Future<Integer> future = executor.submit(new Callable01(prevInt, ins));   '
public class Callable01 implements Callable<Integer> {
    Integer o1;
    Integer o2;

    Callable01(Integer o1, Integer o2){
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " says: partial Sum for " + o1 + " and "+ o2+ " is "  +(o1+o2));
        return o1 + o2;
    }
}

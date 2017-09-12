package com.github.devcat24.util.concurrent;

import lombok.Data;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class DeliveryStore {
    long delayTime = ThreadLocalRandom.current().nextInt(300, 2000);
    private String name;

    public DeliveryStore(String name){
        this.name = name;
    }

    public Future<String> orderAsync(String item) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "[" + name + "] " + item + "Async delivery (elapsed: " + delayTime + ")";
        });
    }

    public String order(String item) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "[" + name + "] " + item + "Sync delivery (elapsed: " + delayTime + ")";
    }

}

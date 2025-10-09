package com.foodapp.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AsyncUtils {
    
    public static <T, R> List<R> parallelExecute(List<T> items, 
                                               Function<T, CompletableFuture<R>> asyncOperation) {
        List<CompletableFuture<R>> futures = items.stream()
            .map(asyncOperation)
            .collect(Collectors.toList());
            
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
            .join();
    }
    
    public static <T, R> List<R> parallelExecuteWithFallback(List<T> items, 
                                                          Function<T, CompletableFuture<R>> asyncOperation,
                                                          Function<Throwable, R> fallback) {
        List<R> results = new ArrayList<>();
        
        List<CompletableFuture<Void>> futures = items.stream()
            .map(item -> asyncOperation.apply(item)
                .exceptionally(fallback)
                .thenAccept(results::add))
            .collect(Collectors.toList());
            
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        
        return results;
    }
    
    public static <T> CompletableFuture<T> withTimeout(CompletableFuture<T> future, long timeoutMillis) {
        CompletableFuture<T> timeout = new CompletableFuture<>();
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                timeout.completeExceptionally(new java.util.concurrent.TimeoutException());
                timer.cancel();
            }
        }, timeoutMillis);
        
        return CompletableFuture.anyOf(future, timeout)
            .thenApply(result -> (T) result);
    }
    
    public static <T> CompletableFuture<T> withRetry(Function<Integer, CompletableFuture<T>> operation, 
                                                    int maxRetries, 
                                                    long delayMillis) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < maxRetries; i++) {
                try {
                    return operation.apply(i).join();
                } catch (Exception e) {
                    if (i == maxRetries - 1) {
                        throw e;
                    }
                    try {
                        Thread.sleep(delayMillis * (i + 1));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(ie);
                    }
                }
            }
            throw new RuntimeException("Max retries exceeded");
        });
    }
}
package com.indicium.services;

import javafx.application.Platform;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * A generic, thread-safe executor service for running database queries 
 * off the main JavaFX Application Thread.
 */
public class AsyncDatabaseExecutor {

    // A cached thread pool is good for database IO tasks as it creates new threads as needed 
    // and reuses previously constructed threads when they are available.
    // Alternatively, Executors.newFixedThreadPool(10) can be used to limit DB connections.
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Executes a runnable task (like an insert or update that returns void) asynchronously.
     * @param task The task to run in the background.
     * @return CompletableFuture to allow chaining or exception handling.
     */
    public static CompletableFuture<Void> runAsync(Runnable task) {
        return CompletableFuture.runAsync(task, executor)
            .exceptionally(ex -> {
                // Gracefully handle exceptions on the background thread without crashing
                System.err.println("[AsyncDB] Background task failed: " + ex.getMessage());
                ex.printStackTrace();
                return null;
            });
    }

    /**
     * Executes a task that returns a result (like a SELECT query or save operation returning boolean)
     * asynchronously.
     * @param supplier The task returning a result.
     * @param <T> The type of the result.
     * @return CompletableFuture containing the result.
     */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor)
            .exceptionally(ex -> {
                System.err.println("[AsyncDB] Background supplier task failed: " + ex.getMessage());
                ex.printStackTrace();
                return null;
            });
    }

    /**
     * Shuts down the executor pool. Call this when exiting the JavaFX Application.
     */
    public static void shutdown() {
        executor.shutdown();
    }
}

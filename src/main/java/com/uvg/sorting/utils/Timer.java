package com.uvg.sorting.utils;

public class Timer {
    private final FileHandler fileHandler;

    public Timer(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void measureSortingTime(String algorithm, int size, Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long timeNanos = System.nanoTime() - startTime;
        
        // Guardar tiempo en el FileHandler
        fileHandler.saveExecutionTime(algorithm, size, timeNanos);
        
        System.out.printf("%s (size %d): %d ns%n", algorithm, size, timeNanos);
    }
}
package com.example.hoangelato.coachridetodevilcastle.Network;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by bloe on 11/08/2016.
 */

public class ThreadHelper {
    public static Object executeTaskWithTimeout(Callable<Object> callable, int timeout ) throws TimeoutException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = executor.submit(callable);

        try {
            Object result = future.get(timeout, TimeUnit.MILLISECONDS);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    public static <T> T executeTaskWithTimeoutNew(Callable<T> callable, int timeout ) throws TimeoutException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> future = executor.submit(callable);

        try {
            T result = future.get(timeout, TimeUnit.MILLISECONDS);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    public static void executeTaskWithTimeoutNew(Runnable runnable, int timeout) throws TimeoutException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(runnable);

        try {
            future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

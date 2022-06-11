package com.cellulam.msg.db.test.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author eric.li
 * @date 2022-06-12 02:24
 */
public class SyncFuture<T> implements Future<T> {
    private T result;

    private ExecutionException exception;

    public SyncFuture() {
        this(null, null);
    }

    public SyncFuture(T result) {
        this(result, null);
    }

    public SyncFuture(ExecutionException exception) {
        this(null, exception);
    }

    private SyncFuture(T result, ExecutionException exception) {
        this.result = result;
        this.exception = exception;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        if (exception != null) {
            throw exception;
        }
        return this.result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.get();
    }
}

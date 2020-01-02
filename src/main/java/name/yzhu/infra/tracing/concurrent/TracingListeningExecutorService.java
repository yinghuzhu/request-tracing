package name.yzhu.infra.tracing.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author yhzhu
 */
public class TracingListeningExecutorService extends TracingExecutor  implements ListeningExecutorService {
    private final ListeningExecutorService executorService;

    public static ListeningExecutorService get(ListeningExecutorService executorService){
        return new TracingListeningExecutorService(executorService);
    }

    TracingListeningExecutorService(ListeningExecutorService executor) {
        super(executor);
        executorService = executor;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> task) {
        return executorService.submit(TracingCallable.get(task));
    }

    @Override
    public ListenableFuture<?> submit(Runnable task) {
        return executorService.submit(TracingRunnable.get(task));
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable task, T result) {
        return executorService.submit(TracingRunnable.get(task), result);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(TracingCallable.gets(tasks));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.invokeAll(TracingCallable.gets(tasks), timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return executorService.invokeAny(TracingCallable.gets(tasks));
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return executorService.invokeAny(TracingCallable.gets(tasks), timeout, unit);
    }
}

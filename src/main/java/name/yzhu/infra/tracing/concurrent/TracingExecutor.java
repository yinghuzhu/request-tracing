package name.yzhu.infra.tracing.concurrent;

import java.util.concurrent.Executor;

/**
 * @author yhzhu
 */
public class TracingExecutor implements Executor {
    protected final Executor executor;

    public static Executor get(Executor executor) {
        return new TracingExecutor(executor);
    }

    TracingExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Runnable command) {
        executor.execute(TracingRunnable.get(command));
    }
}

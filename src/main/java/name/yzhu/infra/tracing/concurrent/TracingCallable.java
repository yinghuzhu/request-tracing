package name.yzhu.infra.tracing.concurrent;

import com.alibaba.ttl.TtlCallable;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author yhzhu
 */
final public class TracingCallable<T> implements Callable<T> {
    private final Callable<T> callable;
    private final Map<String, String> context;

    private TracingCallable(Callable<T> callable) {
        this.callable = TtlCallable.get(callable);
        context = MDC.getCopyOfContextMap();
    }

    public static <T> TracingCallable<T> get(Callable<T> callable){
        return new TracingCallable<>(callable);
    }

    public static <T> List<TracingCallable<T>> gets(Collection<? extends Callable<T>> tasks){
        List<TracingCallable<T>> copy = new ArrayList<>();
        for (Callable<T> task : tasks) {
            copy.add(get(task));
        }
        return copy;
    }
    @Override
    public T call() throws Exception {
        MDC.setContextMap(context);
        try{
            T result = callable.call();
            return result;
        }
        finally {
            MDC.clear();
        }
    }

}

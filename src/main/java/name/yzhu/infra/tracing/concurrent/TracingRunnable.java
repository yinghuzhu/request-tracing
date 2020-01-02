package name.yzhu.infra.tracing.concurrent;

import com.alibaba.ttl.TtlRunnable;
import org.slf4j.MDC;

import java.util.Map;

/**
 * @author yhzhu
 */
final public class TracingRunnable implements Runnable {
    private final Runnable runnable;
    private final Map<String, String> context;

    private TracingRunnable(Runnable runnable){
        this.runnable = TtlRunnable.get(runnable);
        context = MDC.getCopyOfContextMap();
    }

    public static TracingRunnable get(Runnable runnable){
        return new TracingRunnable(runnable);
    }

    @Override
    public void run() {
        MDC.setContextMap(context);
        try {
            runnable.run();
        }
        finally {
            MDC.clear();
        }
    }
}

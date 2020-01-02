package name.yzhu.infra.tracing.concurrent;

import com.alibaba.ttl.TransmittableThreadLocal;
import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

/**
 * @author yhzhu
 */
public class TacingScopeManager implements ScopeManager {
    final TransmittableThreadLocal<TracingScope> ttlScope = new TransmittableThreadLocal();

    public TacingScopeManager() {
    }

    @Override
    public Scope activate(Span span, boolean finishOnClose) {
        return new TracingScope(this, span, finishOnClose);
    }

    @Override
    public Scope active() {
        return this.ttlScope.get();
    }
}
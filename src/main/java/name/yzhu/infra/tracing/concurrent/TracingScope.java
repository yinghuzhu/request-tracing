package name.yzhu.infra.tracing.concurrent;

import io.opentracing.Scope;
import io.opentracing.Span;

/**
 * @author yhzhu
 */
public class TracingScope implements Scope {

    private final TacingScopeManager scopeManager;
    private final Span wrapped;
    private final boolean finishOnClose;
    private final TracingScope toRestore;

    TracingScope(TacingScopeManager scopeManager, Span wrapped, boolean finishOnClose) {
        this.scopeManager = scopeManager;
        this.wrapped = wrapped;
        this.finishOnClose = finishOnClose;
        this.toRestore = scopeManager.ttlScope.get();
        scopeManager.ttlScope.set(this);
    }

    @Override
    public void close() {
        if(this.scopeManager.ttlScope.get() == this) {
            if(this.finishOnClose) {
                this.wrapped.finish();
            }

            this.scopeManager.ttlScope.set(this.toRestore);
        }
    }

    @Override
    public Span span() {
        return this.wrapped;
    }
}

package name.yzhu.infra.tracing.concurrent;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer;

/**
 * @author yhzhu
 */
public class TtlTracerBuilderCustomizer implements TracerBuilderCustomizer {
    @Override
    public void customize(JaegerTracer.Builder builder) {
        builder.withScopeManager(new TacingScopeManager());
    }
}

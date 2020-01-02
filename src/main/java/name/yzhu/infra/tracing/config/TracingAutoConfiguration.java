package name.yzhu.infra.tracing.config;

import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer;
import name.yzhu.infra.tracing.concurrent.TtlTracerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author yhzhu
 */
@ConditionalOnBean(io.opentracing.Tracer.class)
@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class TracingAutoConfiguration {

    @Bean
    public List<TracerBuilderCustomizer> tracerCustomizers(){
        return Arrays.asList(new TtlTracerBuilderCustomizer());
    }
}

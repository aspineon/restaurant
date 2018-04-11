package com.nibado.restaurant.lib.tracing;

import com.uber.jaeger.Tracer;
import io.opentracing.Scope;
import org.junit.Test;

public class TracingConfigTest {
    @Test
    public void test() {
        Tracer tracer = new Tracer.Builder("myServiceName")
                .build();

        Scope scope = tracer.scopeManager().active();
        if (scope != null) {
            scope.span().log("Something");
        }
    }
}
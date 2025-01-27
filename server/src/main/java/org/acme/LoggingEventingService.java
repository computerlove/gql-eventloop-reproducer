package org.acme;

import io.quarkus.logging.Log;
import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.spi.EventingService;

import java.util.concurrent.atomic.AtomicLong;

public class LoggingEventingService implements EventingService {
    private static final AtomicLong current = new AtomicLong();

    @Override
    public void beforeExecute(Context context) {
        Log.info("Before " + current.incrementAndGet());
    }

    @Override
    public void afterExecute(Context context) {
        Log.info("After " + current.decrementAndGet());
    }

    @Override
    public String getConfigKey() {
        return "logging";
    }
}

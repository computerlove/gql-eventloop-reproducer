package org.acme;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.jboss.logging.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@QuarkusMain
public class Main implements QuarkusApplication {
    private final GraphqlClient graphqlClient;
    private final Logger log;

    public Main(GraphqlClient graphqlClient, Logger log) {
        this.graphqlClient = graphqlClient;
        this.log = log;
    }

    @Override
    public int run(String... args) throws Exception {
        log.info("Start");
        var one = new AtomicInteger();
        var two = new AtomicInteger();
        try(var es = Executors.newVirtualThreadPerTaskExecutor()) {
            for(int i = 0; i < 10; i++) {
                es.submit(() -> {
                    while (true) {
                        int num = one.incrementAndGet();
                        log.info("sayHello " + num);
                        try {
                            graphqlClient.sayHello1("Name " + num);
                        } catch (Exception e) {
                            log.error("sayHello1 " + num, e);
                        } finally {
                            log.info("Done sayHello " + num);
                        }
                    }
                });
            }
            for(int i = 0; i < 10; i++) {
                es.submit(() -> {
                    while (true) {
                        int num = two.incrementAndGet();
                        log.info("sayHello2 " + num);
                        try {
                            graphqlClient.sayHello2("Name " + num);
                        } catch (Exception e) {
                            log.error("sayHello2 " + num, e);
                        } finally {
                            log.info("Done sayHello2 " + num);
                        }
                    }
                });
            }
            es.awaitTermination(1, TimeUnit.HOURS);
            log.info("Stop");
        }

        return 0;
    }
}

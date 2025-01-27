package org.acme;

import org.acme.clients.GraphqlClient;
import org.acme.clients.RestClient;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.jboss.logging.Logger;

import java.util.concurrent.atomic.AtomicLong;

@GraphQLApi
public class HelloGraphQLResource {
    private final Logger log;
    private final RestClient restClient;
    private final GraphqlClient graphqlClient;
    private static final AtomicLong current = new AtomicLong();


    public HelloGraphQLResource(Logger log,
                                @org.eclipse.microprofile.rest.client.inject.RestClient RestClient restClient,
                                GraphqlClient graphqlClient) {
        this.log = log;
        this.restClient = restClient;
        this.graphqlClient = graphqlClient;
    }

    @Query
    @Description("Say hello 1")
    public String sayHello1(@DefaultValue("World") String name) {
        try {
            var num = current.incrementAndGet();
            log.info("sayHello1 " + name + " current: " + num);
            return restClient.hello(name);
        } finally {
           // log.info("DONE sayHello1 " + name);
            current.decrementAndGet();
        }
    }

    @Query
    @Description("Say hello 2")
    public String sayHello2(@DefaultValue("World") String name) {
        try {
            var num = current.incrementAndGet();

            log.info("sayHello2 " + name + " current: " + num);
            return graphqlClient.sayHello(name);
        } finally {
            //log.info("DONE sayHello2 " + name);
            current.decrementAndGet();
        }
    }
}
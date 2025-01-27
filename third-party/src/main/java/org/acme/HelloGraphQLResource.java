package org.acme;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.jboss.logging.Logger;

@GraphQLApi
public class HelloGraphQLResource {
    private final Logger log;

    public HelloGraphQLResource(Logger log) {
        this.log = log;
    }

    @Query
    @Description("Say hello")
    public String sayHello(@DefaultValue("World") String name) throws InterruptedException {
        Thread.sleep(2000);
        log.info(name);
        return "Hello " + name;
    }
}
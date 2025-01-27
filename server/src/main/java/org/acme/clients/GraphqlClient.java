package org.acme.clients;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;

@GraphQLClientApi(endpoint = "http://localhost:8081/graphql")
public interface GraphqlClient {
    String sayHello(String name);
}

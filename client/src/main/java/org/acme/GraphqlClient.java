package org.acme;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;

@GraphQLClientApi(endpoint = "http://localhost:8080/graphql")
public interface GraphqlClient {
    String sayHello1(String name);
    String sayHello2(String name);

}
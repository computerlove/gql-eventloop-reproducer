package org.acme.clients;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8081")
public interface RestClient {
    @GET
    @Path("hello")
    String hello(@QueryParam("name") String hello);
}

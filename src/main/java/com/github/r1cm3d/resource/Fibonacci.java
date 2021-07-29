package com.github.r1cm3d.resource;

import com.github.r1cm3d.domain.AsyncFibonacci;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/fib")
public class Fibonacci {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> hello(@QueryParam("n") int n) {
        return AsyncFibonacci.calc(n);
    }
}
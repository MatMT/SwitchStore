package com.dwf.switchstore.ws.endpoints;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// http://localhost:8080/switchstore-1.0-SNAPSHOT/api/index/
@Path("/index")
public class Home {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHome() {return "Welcome to Switch Store! :)"; }
}

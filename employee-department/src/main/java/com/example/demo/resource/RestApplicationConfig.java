package com.example.demo.resource;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApplicationConfig extends ResourceConfig {
    public RestApplicationConfig() {
        packages("com.example.demo.resource");
    }
}

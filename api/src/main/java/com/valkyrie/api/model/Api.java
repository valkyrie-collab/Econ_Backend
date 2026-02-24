package com.valkyrie.api.model;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Api {
    private RouterFunction<ServerResponse> get(String name, String port, String finalDes) {
        return route(name).GET(finalDes, http()).before(uri("http://localhost:" + port)).build();
    }

    private RouterFunction<ServerResponse> post(String name, String port, String finalDes) {
        return route(name).POST(finalDes, http()).before(uri("http://localhost:" + port)).build();
    }

    private RouterFunction<ServerResponse> delete(String name, String port, String finalDes) {
        return route(name).DELETE(finalDes, http()).before(uri("http://localhost:" + port)).build();
    }
    
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return post("auth_post", "8081", "authentication/**")
            .and(
                delete("auth_delete", "8081", "authentication/**")
            ).and(
                post("customer_post", "8082", "customer/**")
            ).and(
                get("customer_get", "8082", "customer/**")
            ).and(
                delete("customer_delete", "8082", "customer/**")
            );
    }
}

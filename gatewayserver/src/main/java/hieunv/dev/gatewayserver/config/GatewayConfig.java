package hieunv.dev.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator easyBankRouteLocatorConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/easybank/accounts/**")
                        .filters(f -> f.rewritePath("/easybank/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/easybank/cards/**")
                        .filters(f -> f.rewritePath("/easybank/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .route(p -> p
                        .path("/easybank/loans/**")
                        .filters(f -> f.rewritePath("/easybank/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))
                .build();
    }
}

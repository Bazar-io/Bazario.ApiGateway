package com.pr0f1t.Bazario.config;

import com.pr0f1t.Bazario.filters.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("identity-service", r -> r.path("/identity/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5002"))
                .route("user-service", r -> r.path("/user/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5004"))
                .route("listing-service", r -> r.path("/listing/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5006"))
                .route("search-service", r -> r.path("/search/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5008"))
                .route("moderation-service", r -> r.path("/moderation/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5010"))
                .route("complaint-service", r -> r.path("/complaint/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5012"))
                .route("notification-service", r -> r.path("/notification/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())))
                        .uri("https://localhost:5014"))
                .build();

    }
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(10, 20);
    }
}

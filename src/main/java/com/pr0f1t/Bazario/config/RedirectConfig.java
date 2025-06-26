package com.pr0f1t.Bazario.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.server.HttpServer;

@Configuration
public class RedirectConfig {

    @PostConstruct
    public void startHttpRedirectServer() {
        HttpServer.create()
                .port(80)
                .route(routes -> routes.route(req -> true,
                        (req, res) -> {
                    String host = req.requestHeaders().get("Host");
                    if (host != null && host.contains(":80")) {
                        host = host.replace(":80", "");
                    }
                    String redirectUrl = "https://" + host + req.uri();
                    return res.status(301)
                            .header("Location", redirectUrl)
                            .send();
                }))
                .bindNow();
    }
}

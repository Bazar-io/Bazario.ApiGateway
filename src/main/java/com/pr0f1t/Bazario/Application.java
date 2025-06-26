package com.pr0f1t.Bazario;

import com.pr0f1t.Bazario.security.EndpointSecurityProperties;
import com.pr0f1t.Bazario.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({EndpointSecurityProperties.class,  JwtProperties.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

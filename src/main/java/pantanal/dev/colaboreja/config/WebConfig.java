package pantanal.dev.colaboreja.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .maxAge(86400)
                .allowedMethods("POST, GET, OPTIONS, PUT, DELETE")
                .allowedHeaders("Content-Type, Authorization")
                .allowedOrigins("http://localhost:8080");
    }
}

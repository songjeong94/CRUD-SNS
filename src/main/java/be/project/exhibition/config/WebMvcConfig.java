package be.project.exhibition.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user/image/**")
                .addResourceLocations("file:/Users/songjeonghyeon/Desktop/sns_image/use_image/");

        registry.addResourceHandler("/post/images/**")
                .addResourceLocations("file:/Users/songjeonghyeon/Desktop/sns_image/post_image/");

    }

}
//package org.example.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
// //todo if using @EnableWebMvc
//@Configuration
//public class WebConfig extends WebMvcConfigurationSupport {
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("**/*.css", "**/*.js", "**/*.map", "*.html")
//                .addResourceLocations("classpath:META-INF/resources/")
//                .setCachePeriod(0);
//    }
//}
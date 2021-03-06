package com.tfgrafsalcas1.airus.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
 
@Configuration
@ComponentScan("com.arquitecturajava.*")
@EnableWebMvc
public class ConfiguracionSpring {
     
    @Bean
     public InternalResourceViewResolver getInternalResourceViewResolver() {
     InternalResourceViewResolver resolver = new InternalResourceViewResolver();
     resolver.setPrefix("/WEB-INF/views/");
     resolver.setSuffix(".jsp");
     return resolver;
 
    }
}
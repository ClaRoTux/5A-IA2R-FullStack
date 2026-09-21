package org.polytech.spring;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.polytech.spring")
@PropertySource("classpath:application.properties")
public class AppConfig {

    // @Bean
    // public PatientStore patientStore() {
    // return new PatientDataBase();
    // }

    // @Bean
    // public PatientService patientService(PatientStore store) {
    // return new PatientService(store);
    // }

}
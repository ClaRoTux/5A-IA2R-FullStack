package org.polytech.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

public class App {
    static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
            PatientService service = ctx.getBean(PatientService.class);
            service.savePatient(new Patient(1, "John"));

            TestScope testScope1 = ctx.getBean(TestScope.class);
            System.out.println("scope1: " + ObjectUtils.identityToString(testScope1));
            TestScope testScope2 = ctx.getBean(TestScope.class);
            System.out.println("scope2: " + ObjectUtils.identityToString(testScope2));
        }
    }
}

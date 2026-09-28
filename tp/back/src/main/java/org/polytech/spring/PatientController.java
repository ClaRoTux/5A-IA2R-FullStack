package org.polytech.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    private PatientService patientService;

    @GetMapping(value = "/hello")
    public Patient hello() {
        return new Patient(2, "Bob", "Michelle");
    }

    @GetMapping("/patient")
    public void finAll() {

    }

}

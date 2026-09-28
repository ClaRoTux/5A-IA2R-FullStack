package org.polytech.spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Repository
@Primary
public class PatientDataBase implements PatientStore {

    private String urlDB;

    @Override
    public void savePatient(Patient p) {
        System.out.println("Sauvegarde en bdd : " + p.getName());
    }

    @PostConstruct
    public void init() {
        System.out.println("ouverture connexion");
    }

    @PreDestroy
    public void close() {
        System.out.println("fermeture connexion");
    }

}
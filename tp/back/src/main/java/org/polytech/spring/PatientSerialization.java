package org.polytech.spring;

import org.springframework.stereotype.Repository;

@Repository
public class PatientSerialization implements PatientStore {

    @Override
    public void savePatient(Patient p) {
        System.out.println("Sauvegarde dans un fichier de " + p.getName());
    }
}

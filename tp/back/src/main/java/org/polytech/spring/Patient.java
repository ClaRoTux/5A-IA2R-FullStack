package org.polytech.spring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;
    @Column(name = "first_name", length = 64, nullable = false)
    private final String first_name;
    private final String last_name;

    @OneToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;

    public Patient(int id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getName() {
        return first_name;
    }

    public int getId() {
        return id;
    }
}
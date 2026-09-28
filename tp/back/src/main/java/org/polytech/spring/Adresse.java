package org.polytech.spring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Adresse {
    @Id
    private int id;
    @Column(name = "Rue", nullable = false, length = 64)
    private String rue;
    @Column(name = "Ville", length = 64, nullable = false)
    private String ville;
    @Column(name = "Code Postale", length = 64, nullable = false)
    private String codepostale;
}

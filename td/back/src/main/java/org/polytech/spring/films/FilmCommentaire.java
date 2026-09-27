package org.polytech.spring.films;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilmCommentaire {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String auteur;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;
    private String message;

    public long getId() {
        return id;
    }

    public String getAuteur() {
        return auteur;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
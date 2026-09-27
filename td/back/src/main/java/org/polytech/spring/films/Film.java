package org.polytech.spring.films;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Film {
    private long id;
    private String titre;
    private String realisateur;
    private LocalDate dateSortie;
    private Genre genre;
    @JsonIgnore
    private List<FilmCommentaire> commentaires = new ArrayList<>();

    public long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public Genre getGenre() {
        return genre;
    }

    public List<FilmCommentaire> getCommentaires() {
        return commentaires;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}

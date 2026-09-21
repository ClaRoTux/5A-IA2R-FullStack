package org.polytech.spring;

public class Patient {

    private final int id;
    private final String name;

    public Patient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
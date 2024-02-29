package com.example.project.model;

public enum Sports {
    NO_SPORT("Select Sport"),
    FOOTBALL("Football"),
    BASKETBALL("Basketball"),
    TENIS("Tenis"),
    TABLE_TENIS("Table Tenis");

    private String displayName;

    Sports(String sport) {
        this.displayName = sport;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
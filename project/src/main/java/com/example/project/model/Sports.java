package com.example.project.model;

public enum Sports {
    FOOTBALL("Football"),
    BASKETBALL("Basketball"),
    TENIS("Tenis"),
    TABLE_TENIS("Table Tenis");

    private String displayName;

    Sports(String sport) {
        this.displayName = sport;
    }
}
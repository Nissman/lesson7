package ru.sviridov.lesson7.awesome_project.model;

public enum Period {
    NOW(1),
    FIVE_DAYS(5);

    private int value;

    Period(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

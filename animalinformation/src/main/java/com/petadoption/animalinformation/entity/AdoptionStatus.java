package com.petadoption.animalinformation.entity;

public enum AdoptionStatus {
    AVAILABLE,
    PENDING,
    ADOPTED;

    public static AdoptionStatus fromString(String status) {
        for (AdoptionStatus s : AdoptionStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + status);
    }
    public static boolean isValidStatus(String status) {
        for (AdoptionStatus s : AdoptionStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}


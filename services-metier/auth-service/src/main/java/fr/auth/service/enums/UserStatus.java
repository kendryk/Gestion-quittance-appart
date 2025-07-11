package fr.auth.service.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {

    ACTIF("actif"),
    INACTIF("inactif"),
    SUSPENDU("suspendu");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}

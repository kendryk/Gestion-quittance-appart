package fr.auth.service.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {

    ADMIN("admin"),
    UTILISATEUR("utilisateur");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}

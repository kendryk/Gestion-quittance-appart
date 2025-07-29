package fr.auth.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserStatus {

    ACTIF("actif"),
    INACTIF("inactif"),
    SUSPENDU("suspendu");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    public static UserStatus fromValue(String value) {
        if (value == null) return null;
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Statut invalide : " + value);
    }

    public String getValue() {
        return value;
    }

}


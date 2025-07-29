package fr.auth.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    ADMIN("admin"),
    UTILISATEUR("utilisateur");

    private final String value;

    public String getValue() {  // Ajout du getter public
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static UserRole fromValue(String value) {
        if (value == null) return null;
        for (UserRole role : UserRole.values()) {
            if (role.getValue().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Role invalide : " + value);
    }

}

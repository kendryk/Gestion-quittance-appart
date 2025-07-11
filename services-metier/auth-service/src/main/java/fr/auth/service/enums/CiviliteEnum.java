package fr.auth.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CiviliteEnum {
    M("M."),
    MME("MME");

    @Getter
    private final String civilite;
}

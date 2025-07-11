package fr.quittance.common.enums;

public enum CiviliteEnum {
    M("M."),
    MME("MME");

    private final String label;

    CiviliteEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Optionnel : méthode pour obtenir l'énumération à partir du label (utile pour les conversions)
    public static CiviliteEnum fromLabel(String label) {
        for (CiviliteEnum civilite : values()) {
            if (civilite.getLabel().equalsIgnoreCase(label)) {
                return civilite;
            }
        }
        throw new IllegalArgumentException("Aucune civilité correspondante pour le label : " + label);
    }

}

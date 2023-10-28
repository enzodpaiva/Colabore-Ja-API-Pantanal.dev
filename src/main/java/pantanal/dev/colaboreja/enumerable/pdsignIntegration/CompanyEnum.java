package pantanal.dev.colaboreja.enumerable.pdsignIntegration;

public enum CompanyEnum {
    PDTEC("5449650b-5406-500f-7c1e-2e3854df84b0"),
    UFMS("ed5b9293-651b-462c-9f8d-262ef31d2d9e"),
    ITAU("0e3b38b4-f646-4080-87ce-4fbc3e2d797d");

    private final String id;

    CompanyEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String getIdFromName(String name) {
        for (CompanyEnum company : values()) {
            if (company.name().equalsIgnoreCase(name)) {
                return company.id;
            }
        }
        return null; // Se o nome não for encontrado, retorne null
    }
}

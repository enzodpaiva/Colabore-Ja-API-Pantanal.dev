package pantanal.dev.colaboreja.enumerable.pdsignIntegration;

public enum AuthenticationTypeEnum {
    SOFT_LOGIN("841c8833-8566-4a9a-be5b-b30839ed138d"),
    LOGIN_E_SENHA("6cd5138e-61ec-47a0-9b85-6f5b4ba1a9db");

    private final String id;

    AuthenticationTypeEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String getIdFromName(String name) {
        for (AuthenticationTypeEnum actionType : values()) {
            if (actionType.name().equalsIgnoreCase(name)) {
                return actionType.id;
            }
        }
        return null; // Se o nome não for encontrado, retorne null
    }
}

package pantanal.dev.colaboreja.enumerable.pdsignIntegration;

public enum ActionTypeEnum {
    ACEITE("510b226e-c705-4120-ad9d-4a19633ea3df"),
    RUBRICAR("f7dadf01-b586-4f5b-b43e-d73aaf6a9940"),
    VALIDAR("f271a775-84b5-489e-8d1f-6c379cf85e8a"),
    ECPF("6ed93b29-df50-49a7-b5f1-195c3ce17a0b");

    private final String id;

    ActionTypeEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String getIdFromName(String name) {
        for (ActionTypeEnum actionType : values()) {
            if (actionType.name().equalsIgnoreCase(name)) {
                return actionType.id;
            }
        }
        return null; // Se o nome não for encontrado, retorne null
    }
}

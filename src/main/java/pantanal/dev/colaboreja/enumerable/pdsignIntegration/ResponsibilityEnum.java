package pantanal.dev.colaboreja.enumerable.pdsignIntegration;

public enum ResponsibilityEnum {
    TESTEMUNHA("bffa24be-b617-dd7e-2e0f-fa1a34eef8b5"),
    ANUENTE("3f7ef68d-fc2a-a881-a454-c5bb270fc941"),
    AVALISTA("461fd7de-bb91-f016-cf04-e76ca8ee2f34"),
    CARTORIO("d4e93d16-1eb1-68a2-2bf8-40dc070c8c30"),
    CONTADOR_RESPONSAVEL("c6f1f07d-89d0-34cc-288f-7a37fe99d26b"),
    CLIENTE("86ff6bcc-f1d5-af77-c768-7e3cf9e3191e"),
    CONTADOR("8494dbf2-98d6-50d2-395e-4bf4a34fa71f"),
    CREDOR("e6918dac-261f-e4fe-bd04-03774b740100"),
    DIRETOR("d6582fab-cf2e-e353-c5b4-f7b31848cab2"),
    EMITENTE("949c6c4b-0ac6-e3b9-2d89-36f5ea04bd89"),
    FIEL_DEPOSITARIO("009e20f1-2a5a-f67e-6f34-caed8384142e"),
    SUPERINTENDENTE_RESPONSAVEL("a18ef85a-6a61-72eb-1e04-12502ec558d1"),
    GERENTE_DE_CONTROLADORIA("46fdc772-bb60-a4c0-51d9-a639f17bc6fb"),
    GARANTIDOR("70c35f72-493b-dca8-8f30-50550f9c9634"),
    PROCURADOR("65ec9c2b-68ce-4aa7-18b9-2c088247c727"),
    REPRESENTANTE("598871e6-4a74-c5de-a34c-525100c683db");

    private final String id;

    ResponsibilityEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String getIdFromName(String name) {
        for (ResponsibilityEnum responsibility : values()) {
            if (responsibility.name().equalsIgnoreCase(name)) {
                return responsibility.id;
            }
        }
        return null; // Se o nome não for encontrado, retorne null
    }
}


package pantanal.dev.colaboreja.enumerable;

public enum SocialActionContractStatusEnum {

    INTERESTED("INTERESTED"),
    DRAFTED("DRAFTED"),
    CREATED("CREATED"),
    PENDIND("PENDIND"),
    CANCELED("CANCELED"),
    DONE("DONE"),
    REJECTED("REJECTED"),
    EXPIRATED("EXPIRATED"),
    RUNNING("RUNNING"),
    EXPIRED("EXPIRED");

    public final String label;

    private SocialActionContractStatusEnum(String label) {
        this.label = label;
    }

    public static SocialActionContractStatusEnum fromString(String text) {
        for (SocialActionContractStatusEnum b : SocialActionContractStatusEnum.values()) {
            if (b.label.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String toString() {
        return this.label.toString();
    }
}

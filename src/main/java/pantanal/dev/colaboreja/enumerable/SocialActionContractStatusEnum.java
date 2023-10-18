package pantanal.dev.colaboreja.enumerable;

public enum SocialActionContractStatusEnum {
    IMAX("IMAX"),
    _3D("3D"),
    _2D("2D");

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

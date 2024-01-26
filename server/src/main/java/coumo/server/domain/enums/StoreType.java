package coumo.server.domain.enums;

public enum StoreType {
    NONE("NONE"),
    ENTERTAINMENT("ENTERTAINMENT"),
    CAFE("CAFE"),
    RETAIL("RETAIL"),
    BEAUTY("BEAUTY"),
    ACADEMY("ACADEMY"),
    RESTAURANT("RESTAURANT");

    private String value;

    StoreType(String value) {
        this.value = value;
    }

    public static StoreType fromString(String value) {
        for (StoreType type : StoreType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null; // or throw IllegalArgumentException
    }
}

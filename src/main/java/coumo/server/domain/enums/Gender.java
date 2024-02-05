package coumo.server.domain.enums;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");
    private String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromString(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null; // or throw IllegalArgumentException
    }
}
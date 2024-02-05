package coumo.server.domain.enums;

import coumo.server.apiPayload.code.BaseErrorCode;
import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;

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
                if (type.equals(StoreType.NONE)) throw new StoreHandler(ErrorStatus.STORE_CATEGORY_BAD_REQUEST);
                return type;
            }
        }

        throw new StoreHandler(ErrorStatus.STORE_CATEGORY_BAD_REQUEST);
        //return StoreType.NONE;
        //return null; // or throw IllegalArgumentException
    }
}

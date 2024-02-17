package coumo.server.domain.enums;

import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;

public enum StampMax {
    SEVEN, EIGHT, NINE, TEN, TWELVE;

    public static int fromInt(StampMax stampMax) {
        switch (stampMax) {
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case TWELVE:
                return 12;
            default:
                throw new IllegalArgumentException("Invalid StampMax value");
        }
    }
}

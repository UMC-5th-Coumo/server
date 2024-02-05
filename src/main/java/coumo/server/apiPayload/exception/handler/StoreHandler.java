package coumo.server.apiPayload.exception.handler;

import coumo.server.apiPayload.code.BaseErrorCode;
import coumo.server.apiPayload.exception.GeneralException;

public class StoreHandler extends GeneralException {
    public StoreHandler(BaseErrorCode code) {
        super(code);
    }
}

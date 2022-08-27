package com.wdw.wdw.exception;

public class InvalidProviderTypeException extends BusinessException {

    public InvalidProviderTypeException() {
        super(ExceptionCode.INVALID_PROVIDER_TYPE);
    }
}

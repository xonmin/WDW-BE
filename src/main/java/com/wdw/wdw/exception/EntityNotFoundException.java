package com.wdw.wdw.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(ExceptionCode.ENTITY_NOT_FOUND);
    }
}

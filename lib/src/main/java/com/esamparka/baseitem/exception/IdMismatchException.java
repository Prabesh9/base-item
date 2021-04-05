package com.esamparka.baseitem.exception;

import org.springframework.http.HttpStatus;

public class IdMismatchException extends BaseGlobalException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IdMismatchException() {
        super(HttpStatus.CONFLICT, "ID AND CONTENT MISMATCHED");
    }

    public IdMismatchException(HttpStatus status, String message) {
        super(status, message);
    }

}

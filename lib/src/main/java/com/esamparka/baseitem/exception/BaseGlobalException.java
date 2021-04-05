package com.esamparka.baseitem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseGlobalException extends ResponseStatusException{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public BaseGlobalException(HttpStatus status, String message) {
        super(status, message);
    }

}

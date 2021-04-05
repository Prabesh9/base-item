package com.esamparka.baseitem.exception;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends BaseGlobalException{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Item Not Found");
    }

	public ItemNotFoundException(HttpStatus status, String message) {
		super(status, message);
	}

} 



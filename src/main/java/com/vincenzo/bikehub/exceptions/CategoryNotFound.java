package com.vincenzo.bikehub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotFound extends ResponseStatusException {
    public CategoryNotFound() {
        super(HttpStatus.NOT_FOUND, "Category not found.");
    }
}

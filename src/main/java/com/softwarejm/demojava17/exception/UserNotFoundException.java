package com.softwarejm.demojava17.exception;

public class UserNotFoundException extends RuntimeException {

    UserNotFoundException(String username) {
        super("No se encontró al usuario " + username);
    }
}

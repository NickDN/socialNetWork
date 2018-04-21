package ru.myproject.dyakins.dao.exception;

public class DAOException extends RuntimeException {
    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
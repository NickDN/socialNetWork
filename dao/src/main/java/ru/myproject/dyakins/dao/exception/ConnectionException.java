package ru.myproject.dyakins.dao.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String msg) {
        super(msg);
    }
}
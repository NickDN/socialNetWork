package ru.myproject.dyakins.util;


import ru.myproject.dyakins.util.exception.NotFoundException;

public class ValidationUtil {
    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "setId=" + id);
    }

    public static void checkNotFoundWithEmail(boolean found, String email) {
        checkNotFound(found, "setEmail=" + email);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "setId=" + id);
    }

    public static <T> T checkNotFoundWithEmail(T object, String email) {
        return checkNotFound(object, "setEmail=" + email);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }
}
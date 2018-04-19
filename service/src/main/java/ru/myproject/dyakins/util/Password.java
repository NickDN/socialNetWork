package ru.myproject.dyakins.util;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    public static String getHashPassword (String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public static boolean checkPasswords (String password, String hashPassword){
        return BCrypt.checkpw(password,hashPassword);
    }
}
package com.example.airbnbb7.validators;

import java.io.IOException;
import java.util.regex.Pattern;

public class UserValidator {

    public static void validator(String password, String name) throws IOException {
        if (name.length() > 2) {
            for (Character i : name.toCharArray()) {
                if (!Character.isAlphabetic(i)) {
                    throw new IOException("Numbers cannot be entered in the name!");
                } else if (i.toString() == " ") {
                    throw new IOException("Space cannot be used in the name!");
                }
            }
        } else {
            throw new IOException("Name must contain at least 3 letters!");
        }
        for (Character i:password.toCharArray()) {
            if (i.toString() == " "){
                throw new IOException("You can't use spaces in your password!");
            }
        }
        if (password.length() != 8) {
            throw new IOException("password must contain 8 characters!");
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}

package test.ui.utils;

import lombok.Getter;

@Getter
public class User {

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;
}

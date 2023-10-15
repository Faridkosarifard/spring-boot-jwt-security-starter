package com.example.demo.web.dto;

import com.example.demo.Validation.ValidPassword;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:41 PM
 */
public class PasswordDto {

    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;

    private String email;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public PasswordDto setEmail(String email) {
        this.email = email;
        return this;
    }
}

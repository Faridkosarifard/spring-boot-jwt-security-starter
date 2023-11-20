package com.example.demo.domain.dto;

import com.example.demo.domain.view.Views;
import com.example.demo.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:41 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {

    @JsonView({Views.User.class, Views.ChangePassword.class})
    private String oldPassword;

    @JsonView(Views.SavePassword.class)
    private  String token;

    @ValidPassword
    @JsonView({Views.SavePassword.class, Views.ChangePassword.class})
    private String newPassword;

    @JsonView({Views.User.class, Views.ChangePassword.class})
    private String email;
}

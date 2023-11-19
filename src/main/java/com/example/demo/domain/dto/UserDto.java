package com.example.demo.domain.dto;

import com.example.demo.validation.ValidEmail;
import com.example.demo.validation.ValidPassword;
import com.example.demo.domain.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:41 PM
 */

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    @JsonView({Views.User.class, Views.Registration.class})
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    @JsonView({Views.User.class, Views.Registration.class})
    private String lastName;

    @ValidPassword
    @JsonView({Views.Registration.class, Views.Login.class})
    private String password;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    @JsonView({Views.User.class, Views.ResetPassword.class, Views.Registration.class})
    private String email;

    @JsonView(Views.User.class)
    private String birthDay;

    @NotNull
    @Size(min=11,max = 11)
    @JsonView(Views.User.class)
    private String phoneNumber;

    @JsonView({Views.Login.class, Views.Registration.class})
    private String username;

    @JsonView({Views.User.class, Views.Registration.class})
    private boolean use2FA;

    @JsonView(Views.User.class)
    private Integer role;

}

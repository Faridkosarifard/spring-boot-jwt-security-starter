package com.example.demo.sping;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/10/2023
 * Time: 4:40 PM
 */
public interface ISecurityUserService {
    String validatePasswordResetToken(String token);
}

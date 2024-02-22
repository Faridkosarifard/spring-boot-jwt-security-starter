package com.example.demo.repository;

import com.example.demo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:31 PM
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);


    User findUserById(Long id);

}

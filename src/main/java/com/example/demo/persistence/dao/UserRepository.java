package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.User;
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
}

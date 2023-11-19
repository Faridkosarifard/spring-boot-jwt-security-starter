package com.example.demo.repository;

import com.example.demo.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:31 PM
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);


    @Override
    void delete(Role role);

}

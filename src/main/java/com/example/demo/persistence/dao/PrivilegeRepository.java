package com.example.demo.persistence.dao;

import com.example.demo.persistence.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 2:31 PM
 */
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}

package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name=:name")
    Role findByName(String name);
}

package com.example.FlowFireHub.Repositories;

import com.example.FlowFireHub.Domains.FlowFire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface FlowFireRepository extends JpaRepository<FlowFire, Long> {

    @Query("select f from FlowFire f where f.username=:username")
    Optional<FlowFire> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE from User where id = :id", nativeQuery = true)
    Integer deleteUserById(Long id);
}
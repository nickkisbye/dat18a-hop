package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.FireFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface FireFlowRepository extends JpaRepository<FireFlow, Long> {

    @Query("select f from FireFlow f where f.username=:username")
    Optional<FireFlow> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE from User where id = :id", nativeQuery = true)
    Integer deleteUserById(Long id);
}

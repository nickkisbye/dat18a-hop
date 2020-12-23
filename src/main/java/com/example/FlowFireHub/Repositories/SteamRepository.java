package com.example.FlowFireHub.Repositories;

import com.example.FlowFireHub.Domains.Steam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SteamRepository extends JpaRepository<Steam, Long> {

    @Query("select s from Steam s where s.username=:username or s.steamid=:steamid")
    Optional<Steam> findByUsernameOrSteamId(String username, String steamid);

    @Modifying
    @Transactional
    @Query(value = "DELETE from User where id = :id", nativeQuery = true)
    Integer deleteUserById(Long id);

}

package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.Steam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface SteamRepository extends JpaRepository<Steam, Long> {

    Steam findById(long id);

    @Query("select s from Steam s where s.username=:username or s.steamid=:steamid")
    Optional<Steam> findByUsernameOrSteamId(String username, String steamid);

}

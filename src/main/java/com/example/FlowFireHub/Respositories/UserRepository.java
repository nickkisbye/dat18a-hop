package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLastName(String lastName);
    User findById(long id);

    @Query("select u from User u where u.username=:username and u.password=:password")
    User validateUser(@Param("username") String username, @Param("password") String password);

    @Query("select u from User u where u.username=:username or u.email=:email")
    Optional<User> findByEmailOrUsername(String username, String email);

    @Query("select u from User u where u.username=:username")
    Optional<User> findByUsername(String username);
}

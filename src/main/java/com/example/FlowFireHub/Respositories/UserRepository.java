package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    @Query("select u from User u where u.username=:username")
    User findByUsername(String username);

    @Query(value = "SELECT username, id FROM User INNER JOIN user_friend ON friend_id = user.id WHERE user_id = :id", nativeQuery = true)
    User findAllCustom();

    @Modifying
    @Transactional
    @Query(value = "DELETE from User where id = :id", nativeQuery = true)
    Integer deleteUserById(Long id);

}

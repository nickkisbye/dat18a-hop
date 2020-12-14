package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    @Query("select u from User u where u.username=:username")
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT User.username FROM User INNER JOIN user_friend ON friend_id = user.id WHERE user_id = :id", nativeQuery = true)
    List findAllFriendById(Long id);

    List<User> findByFriend_id(Long id);


}

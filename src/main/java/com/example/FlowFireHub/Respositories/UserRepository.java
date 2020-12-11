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

    List<User> findByLastName(String lastName);
    User findById(long id);

    @Query("select u from User u where u.username=:username and u.password=:password")
    User validateUser(@Param("username") String username, @Param("password") String password);

    @Query("select u from User u where u.username=:username or u.email=:email")
    Optional<User> findByEmailOrUsername(String username, String email);

    @Query("select u from User u where u.username=:username")
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM user_friends where USER_ID = 1", nativeQuery = true)
    List<User> getAllTest();

//    @Query(value = "SELECT * FROM user_friends", nativeQuery = true)
//    List<User> getAllTest1();

    @Query("select u from User u inner join fetch u.friends where u.id = :id")
    List<User> getAllTest1(long id);

    @Query(value = "SELECT * FROM user inner join user_friends", nativeQuery = true)
    List<User> getAllTest2(long id);

    List<User> findAllFriendsById(Long id);

    List<User> findByFriends_id(Long id);


}

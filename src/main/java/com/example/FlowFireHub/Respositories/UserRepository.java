package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLastName(String lastName);

    User findById(long id);
}

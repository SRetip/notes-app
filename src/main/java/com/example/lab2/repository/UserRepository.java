package com.example.lab2.repository;

import com.example.lab2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);

//    @Query("select u from users u join fetch u.notes where u.id = ?1")
//    User getUserWithNotesById(Long id);

}

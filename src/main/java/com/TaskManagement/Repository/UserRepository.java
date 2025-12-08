package com.TaskManagement.Repository;

import com.TaskManagement.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserEmail(String userEmail);// Optional return type to handle user not found case
//    boolean existsByUserEmail(String userEmail); // Check if email already exists


}

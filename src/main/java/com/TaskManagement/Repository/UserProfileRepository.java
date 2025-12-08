package com.TaskManagement.Repository;

import com.TaskManagement.Entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
     @Override
    Optional<UserProfile> findById(Long aLong);

    Optional<UserProfile> findByUserOrganizationEmail(String userOrganizationMail);
}

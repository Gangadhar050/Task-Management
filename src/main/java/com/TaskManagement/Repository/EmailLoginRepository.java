package com.TaskManagement.Repository;

import com.TaskManagement.Entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLoginRepository extends JpaRepository<EmailLog, Long> {
}

package com.TaskManagement.Repository;

import com.TaskManagement.Entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    Optional<Label>findByLabelName(String labelName);
}

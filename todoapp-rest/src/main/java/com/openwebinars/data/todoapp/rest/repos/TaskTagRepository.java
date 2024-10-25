package com.openwebinars.data.todoapp.rest.repos;

import com.openwebinars.data.todoapp.rest.model.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {

    Optional<TaskTag> findByName(String name);

}

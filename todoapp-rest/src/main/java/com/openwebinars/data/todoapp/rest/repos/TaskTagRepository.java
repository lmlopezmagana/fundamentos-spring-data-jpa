package com.openwebinars.data.todoapp.rest.repos;

import com.openwebinars.data.todoapp.rest.model.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {
}

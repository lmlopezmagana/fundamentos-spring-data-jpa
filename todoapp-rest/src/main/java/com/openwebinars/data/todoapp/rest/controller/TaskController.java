package com.openwebinars.data.todoapp.rest.controller;

import com.openwebinars.data.todoapp.rest.dto.TaskRequest;
import com.openwebinars.data.todoapp.rest.dto.TaskResponse;
import com.openwebinars.data.todoapp.rest.model.BasicTask;
import com.openwebinars.data.todoapp.rest.model.Task;
import com.openwebinars.data.todoapp.rest.model.User;
import com.openwebinars.data.todoapp.rest.repos.TaskRepository;
import com.openwebinars.data.todoapp.rest.repos.TaskTagRepository;
import com.openwebinars.data.todoapp.rest.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/task/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskTagRepository taskTagRepository;

    @PostMapping("/new/basic")
    public ResponseEntity<TaskResponse> newBasicTask(@RequestBody TaskRequest taskRequest) {

        Optional<User> owner = userRepository.findByUsername(taskRequest.username());

        BasicTask task = BasicTask.builder()
                .owner(owner.orElse(null))
                .title(taskRequest.title() != null ? taskRequest.title() : "Sin título")
                .description(taskRequest.description())
                .build();

        task = taskRepository.save(task);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/task/{id}")
                .build(task.getId());


        return ResponseEntity.created(uri).body(TaskResponse.of(task));

    }


    @GetMapping("/")
    public Page<TaskResponse> getAll(@PageableDefault(page=0, size=5, sort = "createdAt") Pageable pageable) {

        Page<Task> result = taskRepository.findAllWithItemsAndTags(pageable);

        if (result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tasks not found");

        return result.map(TaskResponse::of);

    }




}


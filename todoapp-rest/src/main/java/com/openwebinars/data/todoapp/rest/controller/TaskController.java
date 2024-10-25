package com.openwebinars.data.todoapp.rest.controller;

import com.openwebinars.data.todoapp.rest.dto.EditBasicTaskRequest;
import com.openwebinars.data.todoapp.rest.dto.TaskRequest;
import com.openwebinars.data.todoapp.rest.dto.TaskResponse;
import com.openwebinars.data.todoapp.rest.model.*;
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

    @PutMapping("/basic/{id}")
    public ResponseEntity<TaskResponse> editBasicTask(@RequestBody EditBasicTaskRequest editBasicTaskRequest, @PathVariable Long id) {
        if (!taskRepository.existsByIdAndTaskType(BasicTask.class, id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with ID %d not found".formatted(id));
        }
        return ResponseEntity.of(taskRepository.findByIdWithItemsAndTags(id)
                .map(BasicTask.class::cast)
                .map(task -> {
                    task.setTitle(editBasicTaskRequest.title());
                    task.setDescription(editBasicTaskRequest.description());
                    return taskRepository.save(task);
                })
                .map(TaskResponse::of));


    }

    @PutMapping("/checklist/{id}/add/{item}")
    public ResponseEntity<TaskResponse> addItemToChecklist(@PathVariable String item, @PathVariable Long id) {
        if (!taskRepository.existsByIdAndTaskType(CheckListTask.class, id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with ID %d not found".formatted(id));
        }
        return ResponseEntity.of(taskRepository.findByIdWithItemsAndTags(id)
                .map(CheckListTask.class::cast)
                .map(task -> {
                    task.addItem(CheckListItem.builder().text(item).build());
                    return taskRepository.save(task);
                })
                .map(TaskResponse::of)
        );
    }

    @DeleteMapping("/checklist/{id}/del/{item_id}")
    public ResponseEntity<TaskResponse> deleteItemFromChecklist(@PathVariable("item_id") Long itemId, @PathVariable Long id) {
        if (!taskRepository.existsByIdAndTaskType(CheckListTask.class, id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with ID %d not found".formatted(id));
        }
        return ResponseEntity.of(taskRepository.findByIdWithItemsAndTags(id)
                .map(CheckListTask.class::cast)
                .map(task -> {
                    task.removeItemById(itemId);
                    return taskRepository.save(task);
                })
                .map(TaskResponse::of)
        );
    }

    @PutMapping("/checklist/{id}/toggle/{item_id}")
    public ResponseEntity<TaskResponse> toggleItemInCheckList(@PathVariable("item_id") Long itemId, @PathVariable Long id) {
        if (!taskRepository.existsByIdAndTaskType(CheckListTask.class, id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with ID %d not found".formatted(id));
        }
        taskRepository.toggleCheckListItem(id, itemId);
        return ResponseEntity.of(taskRepository.findByIdWithItemsAndTags(id)
                .map(TaskResponse::of)
        );


    }






}


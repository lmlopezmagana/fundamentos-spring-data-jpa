package com.openwebinars.data.todoapp.rest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username, password, email, fullname;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Task> tasks = new ArrayList<>();

    // Helpers

    public void addTask(Task task) {
        tasks.add(task);
        task.setOwner(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setOwner(null);
    }

}

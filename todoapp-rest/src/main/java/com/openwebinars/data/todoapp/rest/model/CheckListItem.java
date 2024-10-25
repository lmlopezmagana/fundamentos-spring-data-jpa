package com.openwebinars.data.todoapp.rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CheckListItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private CheckListTask task;

    private String text;

    @Builder.Default
    private boolean checked = false;

    public boolean toggle() {
        return checked = !checked;
    }
}


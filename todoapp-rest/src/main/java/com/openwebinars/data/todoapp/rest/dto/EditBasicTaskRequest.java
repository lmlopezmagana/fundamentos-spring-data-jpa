package com.openwebinars.data.todoapp.rest.dto;

public record EditBasicTaskRequest(
        String title,
        String description) {
}

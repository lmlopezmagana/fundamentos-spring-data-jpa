package com.example.springdata.asociaciones.repos;

import com.example.springdata.asociaciones.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

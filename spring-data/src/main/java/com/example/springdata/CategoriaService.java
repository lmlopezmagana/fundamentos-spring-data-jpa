package com.example.springdata;

import com.example.springdata.model.Categoria;
import com.example.springdata.model.Producto;
import com.example.springdata.repos.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    // ¡OJO! Es org.springframework.transaction.annotation.Transactional
    // No jakarta.transaction.Transactional
    @Transactional(readOnly = true)
    public Optional<Categoria> cargarCategoria(Long id) {
        Optional<Categoria> categoria = repository.findById(id);

        if (categoria.isPresent()) {
            // Forzamos la carga de los productos
            categoria.get().getProductos().size();
        }

        return categoria;
    }

}

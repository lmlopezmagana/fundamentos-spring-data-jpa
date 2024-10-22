package com.example.springdata.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductoDescripcion {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String marca;

    private String modelo;

    private String url;

    @OneToOne
    @ToString.Exclude
    private Producto producto;

}

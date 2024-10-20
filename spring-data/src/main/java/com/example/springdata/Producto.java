package com.example.springdata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="productos",
        schema="prueba"
)
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private double precio;
}

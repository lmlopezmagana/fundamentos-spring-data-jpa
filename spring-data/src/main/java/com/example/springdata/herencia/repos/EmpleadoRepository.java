package com.example.springdata.herencia.repos;

import com.example.springdata.herencia.model.Empleado;
import com.example.springdata.herencia.model.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query("""
            select g from Gerente g
            """)
    List<Gerente> seleccionarGerentes();

    @Query("""
            select e from Empleado e where type(e) = Gerente
            """)
    List<Gerente> seleccionarGerentesv2();

}

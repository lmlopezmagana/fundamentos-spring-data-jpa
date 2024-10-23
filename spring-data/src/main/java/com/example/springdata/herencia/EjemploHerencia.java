package com.example.springdata.herencia;

import com.example.springdata.herencia.model.*;
import com.example.springdata.herencia.repos.EmpleadoRepository;
import com.example.springdata.herencia.repos.UsuarioRepository;
import com.example.springdata.herencia.repos.ViviendaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EjemploHerencia {

    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ViviendaRepository viviendaRepository;

    //@PostConstruct
    public void run() {

        /*Usuario usuario = Usuario.builder()
                .username("luismilopez")
                .password("12345")
                .email("luismi@openwebinars.net")
                .build();

        usuarioRepository.save(usuario);

        Administrador administrador = Administrador.builder()
                .username("admin")
                .password("admin")
                .email("admin@openwebinars.net")
                .superadmin(true)
                .build();

        usuarioRepository.save(administrador);


        Empleado empleado1 = Empleado.builder()
                .nombre("Empleado 1")
                .email("emple1@empresa.com")
                .departamento("Ventas")
                .telefono("600123456")
                .build();

        Empleado empleado2 = Empleado.builder()
                .nombre("Empleado 2")
                .email("emple2@empresa.com")
                .departamento("Recursos Humanos")
                .telefono("600987654")
                .build();

        Gerente gerente = Gerente.builder()
                .nombre("Gerente")
                .email("gerente@empresa.com")
                .departamento("Dirección")
                .telefono("600000000")
                .tieneMBA(true)
                .build();

        empleadoRepository.saveAll(List.of(empleado1, empleado2, gerente));

        gerente.addSubordinado(empleado1);
        gerente.addSubordinado(empleado2);

        empleadoRepository.saveAll(List.of(empleado1, empleado2));

        System.out.println(gerente);
        System.out.println("Empleados subordinados de %s: %s".formatted(
                gerente.getNombre(),
                gerente.getSubordinados().stream().map(Empleado::toString).collect(Collectors.joining(", "))
        ));

        empleadoRepository.seleccionarGerentes().forEach(System.out::println);

        empleadoRepository.seleccionarGerentesv2().forEach(System.out::println);
        */

        ViviendaEnAlquiler enAlquiler = ViviendaEnAlquiler.builder()
                .numHabitaciones(3)
                .direccion("C/ Rue del Percebe 13")
                .poblacion("Sevilla")
                .provincia("Sevilla")
                .precioMensual(750.0)
                .importeFianza(1500)
                .duracionMinima(12)
                .build();

        ViviendaEnVenta enVenta = ViviendaEnVenta.builder()
                .numHabitaciones(3)
                .direccion("C/ Rue del Percebe 14")
                .poblacion("Sevilla")
                .provincia("Sevilla")
                .precio(150000.0)
                .obraNueva(false)
                .hipotecada(true)
                .build();


        viviendaRepository.save(enAlquiler);
        viviendaRepository.save(enVenta);

        viviendaRepository.findAll()
                .forEach(System.out::println);

    }

}

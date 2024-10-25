package com.example.springdata.consultas;

import com.example.springdata.asociaciones.model.Categoria;
import com.example.springdata.asociaciones.model.Producto;
import com.example.springdata.asociaciones.model.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Consultas con @Query

    @Query("""
            select p from Producto p
            """)
    List<Producto> obtenerTodos();


    @Query("""
            select max(p.precioVenta) 
            from Producto p
            """)
    Double precioMasCaro();

    @Query("""
            select max(p.precioVenta) 
            from Producto p join p.categoria c
            where c.nombre = :nombre
            """)
    Double precioMasCaroDeCategoria(String nombre);

    @Query("""
            select new com.example.springdata.consultas.GetProductoDto(
               p.id, p.nombreProducto, p.precioVenta
            ) from Producto p          
            """)
    List<GetProductoDto> informacionBasica();


    @Query("""
            select p 
            from Producto p join fetch p.categoria c
            where c.id = 1           
            """)
    List<Producto> productosDeCategoria1();

    @Query("""
            select p
            from Producto p left join fetch p.categoria
            """)
    List<Producto> productosConCategoriaSiTienen();

    @Query("""
            select p
            from Producto p left join fetch p.tags t
            where t.nombre IN :tags
            """)
    List<Producto> productosConTags(@Param("tags") List<String> tagsName);


    @Query("""
            select p
            from Producto p left join fetch p.categoria c
            where lower(p.nombreProducto) like lower(concat('%', ?1,'%'))
                  and p.precioVenta between ?2 and ?3
            order by p.precioVenta
            """)
    List<Producto> productosPorNombreYRangoPrecio(String nombre, double precioMenor, double precioMayor);

    // Consultas derivadas

    List<Producto> findTop10ByOrderByPrecioVentaDesc();

    long countByCategoriaNombre(String nombre);

    Optional<Producto> findFirstByCategoriaNombreOrderByPrecioVentaAsc(String nombre);

    List<Producto> findByNombreProductoContainsIgnoreCase(String nombre);

    List<Producto> findByPrecioVentaBetween(double startPrecioVenta, double endPrecioVenta);

    List<Producto> findByNombreProductoContainsIgnoreCaseAndPrecioVentaBetween(
            String nombre, double startPrecioVenta, double endPrecioVenta
    );

    // Consultas DML

    @Transactional
    void deleteByPrecioVentaGreaterThan(double precioVenta);

    // Provoca error de integridad referencial con ProductoDescripcion
    // @Query no "respeta" las operaciones en cascada
    @Transactional
    @Modifying
    @Query("""
            delete from Producto p
            where p.precioVenta > :precio
            """)
    int eliminarPrecioMayorQue(@Param("precio") double p);

    // La única forma de solucionar este problema sería
    // usando una consulta nativa más compleja
    @Transactional
    @Modifying
    @Query(value = """
            WITH productosABorrar as (
                select id 
                from producto
                where precio > ?1
            ), d1 as (
                delete from producto_tag
                where producto_id in (select id from productosABorrar) 
            ), d2 as (
                delete from producto_descripcion
                where producto_id in (select id from productosABorrar) 
            )
            delete from producto
            where id in (select id from productosABorrar); 
            """,
            nativeQuery = true)
    int eliminarPrecioMayorQueNativa(double p);


    // El incremento será un número decimal entre 0 y 1
    @Modifying
    @Transactional
    @Query("""
            update Producto p
            set p.precioVenta = p.precioVenta * (1.0 + :incremento)
            """)
    int incrementarPrecio(@Param("incremento") double inc);


    @Modifying
    @Transactional
    @Query("""
            update Producto p
            set p.precioVenta = p.precioVenta * (1.0 - :descuento)
            where p.categoria = :categoria
            """)
    void ofertaProductosCategoria(Categoria categoria, double descuento);

    // Paginación

    Page<Producto> findByNombreProductoContainsIgnoreCase(String nombre, Pageable pageable);

    @Query("""
            select p
            from Producto p left join fetch p.tags t
            where t.nombre IN :tags
            """)
    Page<Producto> productosConTags(@Param("tags") List<String> tagsName, Pageable pageable);

    // Limit
    List<Producto> findByNombreProductoContainsIgnoreCase(String nombre, Limit limit);

    // Sort

    @Query("""
            select p
            from Producto p left join fetch p.categoria
            """)
    List<Producto> productosConCategoriaSiTienen(Sort sort, Limit limit);


    @Query("""
            select p
            from Producto p left join fetch p.categoria
            """)
    Page<Producto> productosConCategoriaSiTienen(Pageable pageable);


}

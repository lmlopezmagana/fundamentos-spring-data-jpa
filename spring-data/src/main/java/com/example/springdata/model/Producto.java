package com.example.springdata.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "product_generator")
    @SequenceGenerator(name="product_generator",
            sequenceName = "producto_seq", allocationSize = 1)
    private Long id;

    @Column(length = 512)
    private String nombreProducto;

    //@Column(columnDefinition = "TEXT")
    //private String descripcion;

    @OneToOne(
            mappedBy = "producto",
            fetch = FetchType.EAGER
    )
    private ProductoDescripcion descripcion;

    @Column(name = "precio")
    private double precioVenta;

    @ManyToOne
    @JoinColumn(foreignKey =
        @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;

    // Helpers One to One

    public void setProductoDescripcion(ProductoDescripcion descripcion) {
        this.setDescripcion(descripcion);
        descripcion.setProducto(this);
    }

    public void removeProductoDescripcion(ProductoDescripcion descripcion) {
        this.setDescripcion(null);
        descripcion.setProducto(null);
    }




    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Producto producto = (Producto) o;
        return getId() != null && Objects.equals(getId(), producto.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombreProducto = " + nombreProducto + ", " +
                "descripcion = " + descripcion + ", " +
                "precioVenta = " + precioVenta + ", " +
                "categoria = " + categoria + ")";
    }


}

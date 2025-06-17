package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductosHabitacioneId implements Serializable {
    private static final long serialVersionUID = -2457090229442557227L;
    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "id_habitacion", nullable = false)
    private Integer idHabitacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductosHabitacioneId entity = (ProductosHabitacioneId) o;
        return Objects.equals(this.idProducto, entity.idProducto) &&
                Objects.equals(this.idHabitacion, entity.idHabitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idHabitacion);
    }

}
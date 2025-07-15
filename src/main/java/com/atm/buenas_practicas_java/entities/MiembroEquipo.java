package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "MiembroEquipo")
public class MiembroEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Agrega esto si el ID es autogenerado
    @Column(nullable = false)
    private Long id;

    private String nombre;
    private String imagenURL;
    private String descripcion;

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RedSocial> redesSociales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<RedSocial> getRedesSociales() {
        return redesSociales;
    }

    public void setRedesSociales(List<RedSocial> redesSociales) {
        this.redesSociales = redesSociales;
    }

    // Constructor vacío
    public MiembroEquipo() {}

    public MiembroEquipo(String nombre, String imagenURL, String descripcion, List<RedSocial> redesSociales) {
        this.nombre = nombre;
        this.imagenURL = imagenURL;
        this.descripcion = descripcion;
        this.redesSociales = redesSociales;
    }
}







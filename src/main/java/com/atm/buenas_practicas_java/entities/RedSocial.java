package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "redes_sociales")
public class RedSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String url;

    @ManyToOne
    @JoinColumn(name = "miembro_id")
    private MiembroEquipo miembro;

    public RedSocial() {}

    public RedSocial(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public MiembroEquipo getMiembro() { return miembro; }
    public void setMiembro(MiembroEquipo miembro) { this.miembro = miembro; }
}
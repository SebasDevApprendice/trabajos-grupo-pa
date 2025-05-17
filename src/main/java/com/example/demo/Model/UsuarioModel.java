package com.example.demo.Model;

import java.sql.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "usuarios")

public class UsuarioModel {
    @Id
    @Column(name = "id", nullable = false)
    private long Id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "fecha_nac", nullable = false)
    private Date fecha_nac;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "contrasena", nullable = false)
    private String contrasena;
    @Enumerated(EnumType.STRING)
    @Column(name = "usrTipo", nullable = false)
    private TipoUsuario usrTipo;
    

    public UsuarioModel() {
    }

    public UsuarioModel(int Id, String nombre, Date fecha_nac, String email, String contrasena, TipoUsuario usrTipo) {
        this.Id = Id;
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.email = email;
        this.contrasena = contrasena;
        this.usrTipo = usrTipo;
        
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TipoUsuario getUsrTipo() {
        return usrTipo;
    }

    public void setUsrTipo(TipoUsuario usrTipo) {
        this.usrTipo = usrTipo;
    }

}
package com.example.demo.Model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class ClientesModel {
    
    @Id
    @Column(name = "id", nullable = false)
    private long Id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_nac", nullable = false)
    private Date fecha_nac;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "email", nullable = false) // Arreglado "faalse"
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "saldo", nullable = false)
    private double saldo;

    @Column(name = "rol", nullable = false)
    private String rol;

    public ClientesModel() {}

    public ClientesModel(long Id, String nombre, Date fecha_nac, String telefono, String email, String contrasena, String direccion, double saldo, String rol) {
        this.Id = Id;
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.telefono = telefono;
        this.email = email;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.saldo = saldo;
        this.rol = rol;
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

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getSaldo(){
        return saldo;
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

package com.example.demo.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "usuarios")

public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(name = "Nombre", nullable = false)
    private String Nombre;
    @Column(name = "Apellido", nullable = false)
    private String Apellido;
    @Column(name = "Edad", nullable = false)
    private int Edad;
    @Column(name = "NombreUsr", nullable = false)
    private String NombreUsr;
    @Column(name = "Contrasena", nullable = false)
    private String Contrasena;
    @Column(name = "UsrTipo", nullable = false)
    private String UsrTipo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrito_id", referencedColumnName = "id")
    private CarritoModel carrito;

    public UsuarioModel() {
    }

    public UsuarioModel(int Id, String Nombre, String Apellido, int Edad, String NombreUsr, String Contrasena, String UsrTipo) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Edad = Edad;
        this.NombreUsr = NombreUsr;
        this.Contrasena = Contrasena;
        this.UsrTipo = UsrTipo;
    }

    public long getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public String getNombreUsr() {
        return NombreUsr;
    }

    public void setNombreUsr(String NombreUsr) {
        this.NombreUsr = NombreUsr;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }

    public String getUsrTipo() {
        return UsrTipo;
    }

    public void setUsrTipo(String UsrTipo) {
        this.UsrTipo = UsrTipo;
    }

    

    public CarritoModel getCarrito() {
        return carrito;
    }

    public void setCarrito(CarritoModel carrito) {
        this.carrito = carrito;
    }

    @Override
    public String toString() {
        return "Identificacion: " + Id + "/nNombre: " + Nombre + "/nApellido: " + Apellido + "/nEdad: " + Edad + "/nNombreUsr: " + NombreUsr + "/nContrasena: " + Contrasena + "/nUsrTipo: " + UsrTipo;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema_venta.src.main.java.Builder;

import Modelo.Proveedores;

public class ProveedoresBuilder {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    public ProveedoresBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ProveedoresBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProveedoresBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public ProveedoresBuilder setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public ProveedoresBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Proveedores build() {
        return new Proveedores(id, nombre, direccion, telefono, email);
    }
}


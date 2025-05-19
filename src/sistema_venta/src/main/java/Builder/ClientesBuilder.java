/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

import Modelo.Clientes;

public class ClientesBuilder {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    public ClientesBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ClientesBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ClientesBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public ClientesBuilder setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public ClientesBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public Clientes build() {
        return new Clientes(id, nombre, direccion, telefono, email);
    }
}

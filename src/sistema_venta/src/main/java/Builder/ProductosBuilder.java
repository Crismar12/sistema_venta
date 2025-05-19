/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

import Modelo.Producto;

public class ProductosBuilder {
    private int id;
    private String codigo;
    private String nombre;
    private String proveedor;
    private int stock;
    private double precio;

    public ProductosBuilder setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ProductosBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProductosBuilder setProveedor(String proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public ProductosBuilder setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public ProductosBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public Producto build() {
        return new Producto(0, codigo, nombre, proveedor, stock, precio);
    }
}


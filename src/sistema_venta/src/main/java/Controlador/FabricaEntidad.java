/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Builder.ClientesBuilder;
import Builder.ProductosBuilder;
import sistema_venta.src.main.java.Builder.ProveedoresBuilder;
import Builder.VentaBuilder;
import Modelo.Clientes;
import Modelo.Producto;
import Modelo.Proveedores;
import Modelo.Venta;
import Controlador.ClientesDao;
import Controlador.ProductoDao;
import Controlador.ProveedoresDao;
import Controlador.VentaDao;

public class FabricaEntidad {

    private static final ClientesDao clienteDao = new ClientesDao();
    private static final ProductoDao productoDao = new ProductoDao();
    private static final ProveedoresDao proveedorDao = new ProveedoresDao();
    private static final VentaDao ventaDao = new VentaDao();

    // Crear entidades
    public static Producto crearProducto(String codigo, String nombre, String proveedor, int stock, double precio) {
        return new ProductosBuilder()
                .setCodigo(codigo)
                .setNombre(nombre)
                .setProveedor(proveedor)
                .setStock(stock)
                .setPrecio(precio)
                .build();
    }

    public static Clientes crearCliente(String nombre, String direccion, String telefono, String email) {
        return new ClientesBuilder()
                .setNombre(nombre)
                .setDireccion(direccion)
                .setTelefono(telefono)
                .setEmail(email)
                .build();
    }

    public static Venta crearVenta(String cliente, String vendedor, double total) {
        return new VentaBuilder()
                .setCliente(cliente)
                .setVendedor(vendedor)
                .setTotal(total)
                .setFecha(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();
    }

    public static Proveedores crearProveedor(String nombre, String direccion, String telefono, String email) {
        return new ProveedoresBuilder()
                .setNombre(nombre)
                .setDireccion(direccion)
                .setTelefono(telefono)
                .setEmail(email)
                .build();
    }

    // Eliminar entidades
    public static boolean eliminarCliente(int id) {
        return clienteDao.eliminarCliente(id);
    }

    public static boolean eliminarProducto(int id) {
        return productoDao.eliminarProducto(id);
    }

    public static boolean eliminarProveedor(int id) {
        return proveedorDao.eliminarProveedor(id);
    }

    public static boolean eliminarVenta(int id) {
        return ventaDao.eliminarVenta(id);
    }
}

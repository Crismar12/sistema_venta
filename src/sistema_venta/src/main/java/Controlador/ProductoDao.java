/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductoDao maneja las operaciones CRUD de los productos en la base de datos.
 */
public class ProductoDao {

    // Consultas SQL
    private final String INSERT = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?, ?, ?, ?, ?)";
    private final String SELECT_ALL = "SELECT * FROM productos";
    private final String UPDATE = "UPDATE productos SET codigo = ?, nombre = ?, proveedor = ?, stock = ?, precio = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM productos WHERE id = ?";

    /**
     * Método para registrar un nuevo producto en la base de datos.
     */
    public boolean registrarProducto(Producto producto) {
        try (Connection con = ConexionBD.getInstance();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getProveedor());
            ps.setInt(4, producto.getStock());
            ps.setDouble(5, producto.getPrecio());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar todos los productos.
     */
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getInstance();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("proveedor"),
                        rs.getInt("stock"),
                        rs.getDouble("precio")
                );
                lista.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Método para actualizar un producto en la base de datos.
     */
    public boolean actualizarProducto(Producto producto) {
        try (Connection con = ConexionBD.getInstance();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getProveedor());
            ps.setInt(4, producto.getStock());
            ps.setDouble(5, producto.getPrecio());
            ps.setInt(6, producto.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un producto de la base de datos.
     */
    public boolean eliminarProducto(int id) {
        try (Connection con = ConexionBD.getInstance();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VentaDao {

    // 🔹 Atributos de conexión
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // 🔹 Constructor para inicializar la conexión
    public VentaDao() {
        con = ConexionBD.getInstance();
    }

    /**
     * Método para listar todas las ventas.
     */
    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas"; // SQL para listar todas las ventas
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setCliente(rs.getString("cliente"));
                venta.setVendedor(rs.getString("vendedor"));
                venta.setTotal(rs.getDouble("total"));
                venta.setFecha(rs.getTimestamp("fecha"));
                lista.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Método para registrar una nueva venta.
     */
    public boolean registrarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha) VALUES (?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, venta.getCliente());
            ps.setString(2, venta.getVendedor());
            ps.setDouble(3, venta.getTotal());
            ps.setTimestamp(4, venta.getFecha());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar una venta existente en la base de datos.
     */
    public boolean actualizarVenta(Venta venta) {
        String sql = "UPDATE ventas SET cliente = ?, vendedor = ?, total = ?, fecha = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, venta.getCliente());
            ps.setString(2, venta.getVendedor());
            ps.setDouble(3, venta.getTotal());
            ps.setTimestamp(4, venta.getFecha());
            ps.setInt(5, venta.getId());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar una venta de la base de datos.
     */
    public boolean eliminarVenta(int id) {
        String sql = "DELETE FROM ventas WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar venta: " + e.getMessage());
            return false;
        }
    }
}



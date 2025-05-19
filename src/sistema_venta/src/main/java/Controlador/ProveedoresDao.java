/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Proveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ProveedoresDao - Implementación del patrón DAO para la entidad Proveedor.
 */
public class ProveedoresDao {
    
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // Registrar un nuevo proveedor
    public boolean registrarProveedor(Proveedores proveedor) {
        String sql = "INSERT INTO proveedores (nombre, direccion, telefono, email) VALUES (?, ?, ?, ?)";
        try {
            con = ConexionBD.getInstance();
            ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getDireccion());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getEmail());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }

    // Listar todos los proveedores
    public List<Proveedores> listarProveedores() {
        List<Proveedores> listaProveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try {
            con = ConexionBD.getInstance();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Proveedores proveedor = new Proveedores();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setEmail(rs.getString("email"));
                listaProveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar proveedores: " + e.getMessage());
        }
        return listaProveedores;
    }

    // Actualizar datos de un proveedor
    public boolean actualizarProveedor(Proveedores proveedor) {
        String sql = "UPDATE proveedores SET nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        try {
            con = ConexionBD.getInstance();
            ps = con.prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getDireccion());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getEmail());
            ps.setInt(5, proveedor.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un proveedor
    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try {
            con = ConexionBD.getInstance();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
}
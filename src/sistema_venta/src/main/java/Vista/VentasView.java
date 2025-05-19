/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema_venta.src.main.java.Vista;

import Controlador.ConexionBD;
import Controlador.VentaDao;
import Modelo.Venta;
import Controlador.FabricaEntidad;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentasView extends JFrame {

    private JTextField txtId, txtCliente, txtVendedor, txtTotal, txtFecha;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnRegresar, btnClientes, btnProductos, btnVentas, btnProveedores;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private VentaDao ventaDao = new VentaDao();

    public VentasView() {
        setTitle("Gestión de Ventas - BeautyNow");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con fondo
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("/multimedia/fondo_azul_oscuro.jpg"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Panel superior con botones de navegación
        JPanel topPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        topPanel.setOpaque(false);

        btnClientes = crearBotonConIcono("Clientes", "/multimedia/logo_cliente.png");
        btnProductos = crearBotonConIcono("Productos", "/multimedia/logo_producto.png");
        btnProveedores = crearBotonConIcono("Proveedores", "/multimedia/logo_proveedor.png");
        btnVentas = crearBotonConIcono("Ventas", "/multimedia/logo_venta.png");
        btnRegresar = crearBotonConIcono("Principal", "/multimedia/logo_beautynow.png");

        topPanel.add(btnClientes);
        topPanel.add(btnProductos);
        topPanel.add(btnProveedores);
        topPanel.add(btnVentas);
        topPanel.add(btnRegresar);

        // Eventos de navegación
        btnRegresar.addActionListener(e -> {
            dispose(); // 🔹 Cierra la ventana actual
            new Principal().setVisible(true);
        });

        btnProductos.addActionListener(e -> {
            dispose();
            new ProductosView().setVisible(true);
        });

        btnProveedores.addActionListener(e -> {
            dispose();
            new ProveedoresView().setVisible(true);
        });

        btnClientes.addActionListener(e -> {
            dispose();
            new VentasView().setVisible(true);
        });
// Panel de formulario usando MigLayout
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[grow][grow]", "[]10[]"));
        formPanel.setOpaque(false); // Para que sea transparente como en ClientesView

        Font labelFont = new Font("Arial", Font.BOLD, 14);

// ID (no editable)
        formPanel.add(crearLabel("ID:", labelFont), "gap para");
        txtId = new JTextField(20);
        txtId.setEditable(false);
        formPanel.add(txtId, "growx");

// Cliente
        formPanel.add(crearLabel("Cliente:", labelFont), "gap para");
        txtCliente = new JTextField(20);
        formPanel.add(txtCliente, "growx");

// Vendedor
        formPanel.add(crearLabel("Vendedor:", labelFont), "gap para");
        txtVendedor = new JTextField(20);
        formPanel.add(txtVendedor, "growx");

// Total
        formPanel.add(crearLabel("Total:", labelFont), "gap para");
        txtTotal = new JTextField(20);
        formPanel.add(txtTotal, "growx");

// Fecha
        formPanel.add(crearLabel("Fecha:", labelFont), "gap para");
        txtFecha = new JTextField(20);
        formPanel.add(txtFecha, "growx");

        // Panel de botones CRUD
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        buttonPanel.setOpaque(false);

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        // Aumentar tamaño de los botones
        btnAgregar.setPreferredSize(new Dimension(150, 50));  // Aumenta el tamaño (ancho, alto)
        btnActualizar.setPreferredSize(new Dimension(150, 50));
        btnEliminar.setPreferredSize(new Dimension(150, 50));
        btnLimpiar.setPreferredSize(new Dimension(150, 50));

        Font botonFont = new Font("Arial", Font.BOLD, 16);  // Cambiar el tamaño de la fuente

        btnAgregar.setFont(botonFont);
        btnActualizar.setFont(botonFont);
        btnEliminar.setFont(botonFont);
        btnLimpiar.setFont(botonFont);

        // Ajustar el espaciado entre los botones CRUD
        buttonPanel.setLayout(new GridLayout(1, 4, 20, 0)); // Aumentar el espacio horizontal entre los botones

        btnAgregar.addActionListener(e -> agregarVenta());
        btnActualizar.addActionListener(e -> actualizarVenta());
        btnEliminar.addActionListener(e -> eliminarVenta());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Tabla de ventas
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Cliente", "Vendedor", "Total", "Fecha"}, 0);
        tablaVentas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        JPanel tablaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tablaPanel.setOpaque(false);
        tablaPanel.add(scrollPane);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(tablaPanel, BorderLayout.CENTER);

        tablaVentas.getSelectionModel().addListSelectionListener(event -> {
            int filaSeleccionada = tablaVentas.getSelectedRow();
            if (filaSeleccionada >= 0) {
                txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                txtCliente.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                txtVendedor.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                txtTotal.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());

                // 🔹 Asignar fecha correctamente
                Object fechaObj = modeloTabla.getValueAt(filaSeleccionada, 4);
                if (fechaObj != null) {
                    txtFecha.setText(fechaObj.toString());
                } else {
                    txtFecha.setText(""); // Si la fecha está vacía, evitar errores
                }
            }
        });

        add(mainPanel);
        cargarDatos();
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, String label, JTextField field, Font font, boolean editable) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(crearLabel(label, font), gbc);
        gbc.gridx = 1;
        field.setEditable(editable);
        panel.add(field, gbc);
    }

    private JButton crearBotonConIcono(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new JButton(text, new ImageIcon(image));
    }

    private JLabel crearLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        return label;
    }
    private boolean validarPrecio(String precioTexto) {
        String precioRegex = "^\\d+(\\.\\d{1,2})?$"; // 🔹 Números con hasta dos decimales
        return precioTexto.matches(precioRegex);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Venta> lista = ventaDao.listarVentas();
        for (Venta v : lista) {
            modeloTabla.addRow(new Object[]{v.getId(), v.getCliente(), v.getVendedor(), v.getTotal(), v.getFecha()});
        }
    }
    private void agregarVenta() {
        try {
            String totalTexto = txtTotal.getText().trim();

            if (!validarPrecio(totalTexto)) {
                JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número válido para el total de ventas.");
                return;
            }

            // 🔹 Se genera automáticamente la fecha actual al momento de guardar
            Timestamp fecha = new Timestamp(System.currentTimeMillis());
            double total = Double.parseDouble(totalTexto);

            Venta venta = FabricaEntidad.crearVenta(txtCliente.getText(), txtVendedor.getText(), total);
            venta.setFecha(fecha); // 🔹 Se asigna la fecha actual
            ventaDao.registrarVenta(venta);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Venta agregada exitosamente con fecha automática.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar venta: " + e.getMessage());
        }
    }

    private void actualizarVenta() {
        try {
            int fila = tablaVentas.getSelectedRow();
            if (fila >= 0) {
                String totalTexto = txtTotal.getText().trim();

                if (!validarPrecio(totalTexto)) {
                    JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número válido para el total de ventas.");
                    return;
                }

                int id = (int) modeloTabla.getValueAt(fila, 0);
                double total = Double.parseDouble(totalTexto);

                // 🔹 Obtener y verificar la fecha antes de la conversión
                String fechaTexto = txtFecha.getText().trim();
                System.out.println("Fecha antes de conversión: '" + fechaTexto + "'"); // Verificación

                if (fechaTexto.isEmpty()) {
                    throw new IllegalArgumentException("El campo de fecha está vacío.");
                }

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fechaDate = formato.parse(fechaTexto);
                Timestamp fecha = new Timestamp(fechaDate.getTime()); // Conversión segura

                Venta venta = FabricaEntidad.crearVenta(txtCliente.getText(), txtVendedor.getText(), total);
                venta.setId(id);
                venta.setFecha(fecha);  // 🔹 Se asigna la fecha correctamente

                ventaDao.actualizarVenta(venta);
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "✅ Venta actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Seleccione una venta para actualizar.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "❌ Error en el formato de fecha: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar venta: " + e.getMessage());
        }
    }

    private void eliminarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloTabla.getValueAt(fila, 0);

            if (FabricaEntidad.eliminarVenta(id)) {
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "✅ Venta eliminada exitosamente.");

                // 🔹 Reorganizar los IDs después de la eliminación
                try (Connection conn = ConexionBD.getInstance(); Statement stmt = conn.createStatement()) {

                    // 🔹 Paso 1: Renumerar IDs correctamente usando ROW_NUMBER()
                    String sqlRenumerar = "UPDATE ventas v JOIN (SELECT id, ROW_NUMBER() OVER() AS nuevo_id FROM ventas) tmp "
                            + "ON v.id = tmp.id SET v.id = tmp.nuevo_id;";
                    stmt.executeUpdate(sqlRenumerar);

                    // 🔹 Paso 2: Obtener el nuevo MAX(id) antes de actualizar AUTO_INCREMENT
                    String sqlMaxId = "SELECT MAX(id) FROM ventas";
                    java.sql.ResultSet rs = stmt.executeQuery(sqlMaxId);
                    int nextId = 1;
                    if (rs.next()) {
                        nextId = rs.getInt(1) + 1;
                    }

                    // 🔹 Paso 3: Actualizar AUTO_INCREMENT con el nuevo valor
                    String sqlAutoIncrement = "ALTER TABLE ventas AUTO_INCREMENT = " + nextId;
                    stmt.executeUpdate(sqlAutoIncrement);

                } catch (SQLException e) {
                    System.out.println("❌ Error al reorganizar los IDs: " + e.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar la venta.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Seleccione una venta para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtCliente.setText("");
        txtVendedor.setText("");
        txtTotal.setText("");
        txtFecha.setText("");
    }
}

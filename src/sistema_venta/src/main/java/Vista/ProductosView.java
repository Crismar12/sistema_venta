/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema_venta.src.main.java.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Controlador.FabricaEntidad;
import Modelo.Producto;
import Controlador.ProductoDao;
import Controlador.ConexionBD;
import net.miginfocom.swing.MigLayout;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class ProductosView extends JFrame {
    private JTextField txtId, txtCodigo, txtNombre, txtProveedor, txtStock, txtPrecio;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnRegresar, btnClientes, btnProductos, btnVentas, btnProveedores;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private ProductoDao productoDao = new ProductoDao();

    public ProductosView() {
        setTitle("Gestión de Productos - BeautyNow");
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

        // Top panel para los botones de navegación
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

        btnClientes.addActionListener(e -> {
            dispose();
            new ProductosView().setVisible(true);
        });

        btnProveedores.addActionListener(e -> {
            dispose();
            new ProveedoresView().setVisible(true);
        });

        btnVentas.addActionListener(e -> {
            dispose();
            new VentasView().setVisible(true);
        });
        
        JPanel formPanel = new JPanel(new MigLayout("wrap 2", "[grow][grow]", "[]10[]"));
        formPanel.setOpaque(false);

        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // ID (no editable)
        formPanel.add(crearLabel("ID:", labelFont), "gap para");
        txtId = new JTextField(20);
        txtId.setEditable(false);
        formPanel.add(txtId, "growx");

        // Código
        formPanel.add(crearLabel("Código:", labelFont), "gap para");
        txtCodigo = new JTextField(20);
        formPanel.add(txtCodigo, "growx");

        // Nombre
        formPanel.add(crearLabel("Nombre:", labelFont), "gap para");
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, "growx");

        // Proveedor
        formPanel.add(crearLabel("Proveedor:", labelFont), "gap para");
        txtProveedor = new JTextField(20);
        formPanel.add(txtProveedor, "growx");

        // Stock
        formPanel.add(crearLabel("Stock:", labelFont), "gap para");
        txtStock = new JTextField(20);
        formPanel.add(txtStock, "growx");

        // Precio
        formPanel.add(crearLabel("Precio:", labelFont), "gap para");
        txtPrecio = new JTextField(20);
        formPanel.add(txtPrecio, "growx");

        // Panel de botones CRUD
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setOpaque(false); 

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar"); 

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Código", "Nombre", "Proveedor", "Stock", "Precio"}, 0);
        tablaProductos = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaProductos); 
        
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaProductos.getSelectedRow();
                if (fila >= 0) {
                    txtCodigo.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtProveedor.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
                    txtPrecio.setText(modeloTabla.getValueAt(fila, 5).toString());
                }
            }
        });

        scrollPane.setPreferredSize(new Dimension(800, 300)); 
        JPanel tablaPanel = new JPanel();
        tablaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablaPanel.setOpaque(false);
        tablaPanel.add(scrollPane);

        mainPanel.add(tablaPanel, BorderLayout.CENTER);
        cargarDatos();

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.WEST);

        add(mainPanel);

        btnAgregar.addActionListener(e -> agregarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
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
    }

    private JButton crearBotonConIcono(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(image));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private JLabel crearLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        return label;
    }

    private boolean validarStock(String stockTexto) {
        String stockRegex = "^\\d+$"; // 🔹 Solo números enteros positivos
        return stockTexto.matches(stockRegex);
    }

    private boolean validarPrecio(String precioTexto) {
        String precioRegex = "^\\d+(\\.\\d{1,2})?$"; // 🔹 Números con hasta dos decimales
        return precioTexto.matches(precioRegex);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Producto> productos = productoDao.listarProductos();
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getCodigo(), p.getNombre(), p.getProveedor(), p.getStock(), p.getPrecio()});
        }
    }

    private void agregarProducto() {
        String stockTexto = txtStock.getText().trim();
        String precioTexto = txtPrecio.getText().trim();

        if (!validarStock(stockTexto)) {
            JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número entero válido para el stock.");
            return;
        }

        if (!validarPrecio(precioTexto)) {
            JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número decimal válido para el precio.");
            return;
        }

        int stock = Integer.parseInt(stockTexto);
        double precio = Double.parseDouble(precioTexto);

        Producto producto = FabricaEntidad.crearProducto(txtCodigo.getText(), txtNombre.getText(), txtProveedor.getText(), stock, precio);
        productoDao.registrarProducto(producto);
        cargarDatos();
        limpiarCampos();
        JOptionPane.showMessageDialog(this, "✅ Producto agregado exitosamente.");
    }

    private void actualizarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            String stockTexto = txtStock.getText().trim();
            String precioTexto = txtPrecio.getText().trim();

            if (!validarStock(stockTexto)) {
                JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número entero válido para el stock.");
                return;
            }

            if (!validarPrecio(precioTexto)) {
                JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número decimal válido para el precio.");
                return;
            }

            int stock = Integer.parseInt(stockTexto);
            double precio = Double.parseDouble(precioTexto);

            int id = (int) modeloTabla.getValueAt(fila, 0);
            Producto producto = FabricaEntidad.crearProducto(txtCodigo.getText(), txtNombre.getText(), txtProveedor.getText(), stock, precio);
            producto.setId(id);
            productoDao.actualizarProducto(producto);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Producto actualizado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Seleccione un producto para actualizar.");
        }
    }

    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modeloTabla.getValueAt(fila, 0);

            if (productoDao.eliminarProducto(id)) {
                cargarDatos();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "✅ Producto eliminado exitosamente.");

                // 🔹 Reorganizar los IDs después de la eliminación
                try (Connection conn = ConexionBD.getInstance(); Statement stmt = conn.createStatement()) {

                    // 🔹 Paso 1: Renumerar IDs correctamente usando ROW_NUMBER()
                    String sqlRenumerar = "UPDATE productos p JOIN (SELECT id, ROW_NUMBER() OVER() AS nuevo_id FROM productos) tmp "
                            + "ON p.id = tmp.id SET p.id = tmp.nuevo_id;";
                    stmt.executeUpdate(sqlRenumerar);

                    // 🔹 Paso 2: Obtener el nuevo MAX(id) antes de actualizar AUTO_INCREMENT
                    String sqlMaxId = "SELECT MAX(id) FROM productos";
                    java.sql.ResultSet rs = stmt.executeQuery(sqlMaxId);
                    int nextId = 1;
                    if (rs.next()) {
                        nextId = rs.getInt(1) + 1;
                    }

                    // 🔹 Paso 3: Actualizar AUTO_INCREMENT con el nuevo valor
                    String sqlAutoIncrement = "ALTER TABLE productos AUTO_INCREMENT = " + nextId;
                    stmt.executeUpdate(sqlAutoIncrement);

                } catch (SQLException e) {
                    System.out.println("❌ Error al reorganizar los IDs: " + e.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar el producto.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Seleccione un producto para eliminar.");
        }
    }


    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtProveedor.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
    }
}

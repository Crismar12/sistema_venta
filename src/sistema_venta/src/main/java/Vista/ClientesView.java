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
import Modelo.Clientes;
import Controlador.ClientesDao;
import Controlador.ConexionBD;
import net.miginfocom.swing.MigLayout;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class ClientesView extends JFrame {
    private JTextField txtId, txtNombre, txtDireccion, txtTelefono, txtEmail;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar, btnRegresar, btnClientes, btnProductos, btnVentas, btnProveedores;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClientesDao clienteDao = new ClientesDao();

    public ClientesView() {
        setTitle("Gestión de Clientes - BeautyNow");
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

        btnProductos.addActionListener(e -> {
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
        formPanel.setOpaque(false); // Para que sea transparente como estaba antes

        Font labelFont = new Font("Arial", Font.BOLD, 14);

// ID (sigue siendo no editable)
        formPanel.add(crearLabel("ID:", labelFont), "gap para");
        txtId = new JTextField(20);
        txtId.setEditable(false);
        formPanel.add(txtId, "growx");

// Nombre
        formPanel.add(crearLabel("Nombre:", labelFont), "gap para");
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, "growx");

// Dirección
        formPanel.add(crearLabel("Dirección:", labelFont), "gap para");
        txtDireccion = new JTextField(20);
        formPanel.add(txtDireccion, "growx");

// Teléfono
        formPanel.add(crearLabel("Teléfono:", labelFont), "gap para");
        txtTelefono = new JTextField(20);
        formPanel.add(txtTelefono, "growx");

// Email
        formPanel.add(crearLabel("Email:", labelFont), "gap para");
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, "growx");


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

        // Tabla
        // Inicialización del modelo y la tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Dirección", "Teléfono", "Email"}, 0);
        tablaClientes = new JTable(modeloTabla);

        // Añadir la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tablaClientes); 
        
        // Evento para cargar los datos de la fila seleccionada en los campos de texto
        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaClientes.getSelectedRow();
                if (fila >= 0) {
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtDireccion.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtEmail.setText(modeloTabla.getValueAt(fila, 4).toString());
                }
            }
        });


       // Establecer el color de fondo de la tabla
        tablaClientes.setBackground(Color.WHITE); 

      // Establecer un tamaño preferido para el JScrollPane
        scrollPane.setPreferredSize(new Dimension(800, 300)); // Ancho: 800, Alto: 300

      // Crear un panel para envolver el JScrollPane
      // Crear un panel para envolver el JScrollPane
        JPanel tablaPanel = new JPanel();
        tablaPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Para centrar
        tablaPanel.setOpaque(false); // 🔹 Hacer el panel transparente
        tablaPanel.add(scrollPane);

        
        // Agregar el panel al mainPanel
        mainPanel.add(tablaPanel, BorderLayout.CENTER);

        cargarDatos();

        // Configuración de paneles
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.WEST);

        add(mainPanel);

        // Listeners
        btnAgregar.addActionListener(e -> agregarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarCampos());
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
    
    private boolean validarEmail(String email) {
    String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,6}$";
    return email.matches(emailRegex);
}
    private boolean validarTelefono(String telefono) {
    String telefonoRegex = "^\\d{8,15}$"; // 🔹 Solo números, mínimo 8 dígitos, máximo 15
    return telefono.matches(telefonoRegex);
}

     private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Clientes> clientes = clienteDao.listarClientes();
        for (Clientes c : clientes) {
            modeloTabla.addRow(new Object[]{c.getId(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.getEmail()});
        }
    }

    private void agregarCliente() {
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (!validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un correo electrónico válido.");
            return;
        }

        if (!validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número de teléfono válido.");
            return;
        }

        if (validarCampos()) {
            Clientes cliente = FabricaEntidad.crearCliente(txtNombre.getText(), txtDireccion.getText(), telefono, email);
            clienteDao.registrarCliente(cliente);
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Cliente agregado exitosamente.");
        }
    }

    private void actualizarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila >= 0) {
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (!validarEmail(email)) {
                JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un correo electrónico válido.");
                return;
            }

            if (!validarTelefono(telefono)) {
                JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un número de teléfono válido.");
                return;
            }

            int id = (int) modeloTabla.getValueAt(fila, 0);

            if (validarCampos()) {
                Clientes cliente = FabricaEntidad.crearCliente(txtNombre.getText(), txtDireccion.getText(), telefono, email);
                cliente.setId(id);

                if (clienteDao.actualizarCliente(cliente)) {
                    cargarDatos();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "✅ Cliente actualizado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al actualizar el cliente.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Seleccione un cliente para actualizar.");
        }
    }


private void eliminarCliente() {
    int fila = tablaClientes.getSelectedRow();
    if (fila >= 0) {
        int id = (int) modeloTabla.getValueAt(fila, 0);

        if (FabricaEntidad.eliminarCliente(id)) {
            cargarDatos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Cliente eliminado exitosamente.");

            // 🔹 Reorganizar los IDs después de la eliminación
            try (Connection conn = ConexionBD.getInstance();
                 Statement stmt = conn.createStatement()) {

                // 🔹 Paso 1: Renumerar IDs correctamente usando ROW_NUMBER()
                String sqlRenumerar = "UPDATE clientes c JOIN (SELECT id, ROW_NUMBER() OVER() AS nuevo_id FROM clientes) tmp " +
                                      "ON c.id = tmp.id SET c.id = tmp.nuevo_id;";
                stmt.executeUpdate(sqlRenumerar);

                // 🔹 Paso 2: Obtener el nuevo MAX(id) antes de actualizar AUTO_INCREMENT
                String sqlMaxId = "SELECT MAX(id) FROM clientes";
                java.sql.ResultSet rs = stmt.executeQuery(sqlMaxId);
                int nextId = 1;
                if (rs.next()) {
                    nextId = rs.getInt(1) + 1;
                }

                // 🔹 Paso 3: Actualizar AUTO_INCREMENT con el nuevo valor
                String sqlAutoIncrement = "ALTER TABLE clientes AUTO_INCREMENT = " + nextId;
                stmt.executeUpdate(sqlAutoIncrement);

            } catch (SQLException e) {
                System.out.println("❌ Error al reorganizar los IDs: " + e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar el cliente.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "❌ Seleccione un cliente para eliminar.");
    }
}


    private void limpiarCampos() {
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }
}

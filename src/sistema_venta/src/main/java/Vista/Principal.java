/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema_venta.src.main.java.Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;


public class Principal extends JFrame {

    private JButton btnClientes, btnProductos, btnProveedores, btnVentas;
    private JLabel logoLabel;

    public Principal() {
        initComponents();
    }

    private void initComponents() {
        // Configuración de la ventana principal
        setTitle("BeautyNow - Sistema de Ventas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con fondo personalizado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("/multimedia/fondo_azul_oscuro.jpg"));
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Logo centrado
        logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon logo = new ImageIcon(getClass().getResource("/multimedia/logo_beautynow.png"));
        logoLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(1, 4, 20, 0));

        // Botones con iconos y estilos
        btnClientes = crearBoton("Clientes", "/multimedia/logo_cliente.png");
        btnProductos = crearBoton("Productos", "/multimedia/logo_producto.png");
        btnProveedores = crearBoton("Proveedores", "/multimedia/logo_proveedor.png");
        btnVentas = crearBoton("Ventas", "/multimedia/logo_venta.png");

        buttonPanel.add(btnClientes);
        buttonPanel.add(btnProductos);
        buttonPanel.add(btnProveedores);
        buttonPanel.add(btnVentas);

        // Eventos de navegación
        btnClientes.addActionListener(e -> new ClientesView().setVisible(true));
        btnProductos.addActionListener(e -> new ProductosView().setVisible(true));
        btnProveedores.addActionListener(e -> new ProveedoresView().setVisible(true));
        btnVentas.addActionListener(e -> new VentasView().setVisible(true));

        // Agregar el logo y el panel de botones al panel principal
        mainPanel.add(logoLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Método para crear un botón estilizado
    private JButton crearBoton(String titulo, String rutaIcono) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaIcono));
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

        JButton boton = new JButton(titulo, iconoRedimensionado);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(33, 150, 243));
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(10, 10, 10, 10));
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(30, 136, 229));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(33, 150, 243));
            }
        });

        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Principal().setVisible(true);
        });
    }
}



package vista;

import controlador.ControladorCancelarCita;
import modelo.Cita;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaCancelarCita extends JFrame {

    private ControladorCancelarCita controlador;
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;

    private JButton btnCancelar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnVolver;

    private DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VistaCancelarCita() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("❌ Cancelar Citas - Sistema de Gestión");
        setSize(900, 650); // Aumentado el tamaño
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 246, 250));
    }

    private void inicializarComponentes() {
        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(231, 76, 60));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel(" Mis Citas Agendadas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Seleccione una cita para cancelar o eliminar");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(Color.WHITE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(5));
        panel.add(sub);

        return panel;
    }

    private JPanel crearPanelTabla() {
        String[] columnas = {"ID", "Nombre", "Email", "Teléfono", "Fecha y Hora", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setRowHeight(35);
        tablaCitas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaCitas.getTableHeader().setBackground(new Color(52, 73, 94));
        tablaCitas.getTableHeader().setForeground(Color.WHITE);

        // Configurar anchos de columnas
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(180); // Email
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(100); // Teléfono
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(140); // Fecha
        tablaCitas.getColumnModel().getColumn(5).setPreferredWidth(100); // Estado

        // Personalizar el renderizado de la columna Estado
        tablaCitas.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String estado = value.toString();
                if (estado.equalsIgnoreCase("Confirmada")) {
                    c.setForeground(new Color(46, 204, 113));
                } else if (estado.equalsIgnoreCase("Cancelada")) {
                    c.setForeground(new Color(231, 76, 60));
                }
                
                if (!isSelected) {
                    c.setBackground(Color.WHITE);
                }
                
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // CORREGIDO: Panel de botones con FlowLayout explícito
    private JPanel crearPanelBotones() {
        // IMPORTANTE: Usar FlowLayout para que los botones se muestren
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(245, 246, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Crear botones
        btnVolver = crearBoton("⬅ Volver", new Color(52, 73, 94));
        btnActualizar = crearBoton(" Actualizar", new Color(52, 152, 219));
        btnCancelar = crearBoton(" Cancelar Cita", new Color(231, 76, 60));
        btnEliminar = crearBoton("️ Eliminar Cita", new Color(149, 165, 166));

        // Agregar listeners
        btnVolver.addActionListener(e -> dispose());
        btnActualizar.addActionListener(e -> {
            controlador.actualizarListaCitas();
            mostrarMensaje("Lista actualizada");
        });
        btnCancelar.addActionListener(e -> cancelarCitaSeleccionada());
        btnEliminar.addActionListener(e -> eliminarCitaSeleccionada());

        // Agregar botones al panel
        panel.add(btnVolver);
        panel.add(btnActualizar);
        panel.add(btnCancelar);
        panel.add(btnEliminar);

        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(190, 45));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(color);
            }
        });
        
        return b;
    }

    private void cancelarCitaSeleccionada() {
        int fila = tablaCitas.getSelectedRow();

        if (fila == -1) {
            mostrarError("Por favor, seleccione una cita de la tabla");
            return;
        }

        String estado = modeloTabla.getValueAt(fila, 5).toString();
        if (estado.equalsIgnoreCase("Cancelada")) {
            mostrarError("Esta cita ya está cancelada");
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        String nombre = modeloTabla.getValueAt(fila, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de cancelar la cita de " + nombre + "?",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            controlador.cancelarCita(id);
        }
    }

    private void eliminarCitaSeleccionada() {
        int fila = tablaCitas.getSelectedRow();

        if (fila == -1) {
            mostrarError("Por favor, seleccione una cita de la tabla");
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        String nombre = modeloTabla.getValueAt(fila, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "⚠️ ¿Eliminar PERMANENTEMENTE la cita de " + nombre + "?\n" +
                "Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            controlador.eliminarCita(id);
        }
    }

    // ========== MÉTODOS PÚBLICOS USADOS POR EL CONTROLADOR ==========

    public void setControlador(ControladorCancelarCita c) {
        controlador = c;
    }

    public void actualizarTabla(List<Cita> citas) {
        modeloTabla.setRowCount(0);

        if (citas.isEmpty()) {
            // Mostrar mensaje cuando no hay citas
            JLabel lblSinCitas = new JLabel("No hay citas registradas");
            lblSinCitas.setHorizontalAlignment(SwingConstants.CENTER);
            lblSinCitas.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            lblSinCitas.setForeground(Color.GRAY);
        }

        for (Cita c : citas) {
            modeloTabla.addRow(new Object[]{
                c.getId(),
                c.getNombreUsuario(),
                c.getEmail(),
                c.getTelefono(),
                c.getFechaHora().format(formatter),
                c.getEstado()
            });
        }

        System.out.println(" Tabla actualizada con " + citas.size() + " citas");
    }

    public void mostrarExito(String m) {
        JOptionPane.showMessageDialog(this, m, " Operación Exitosa",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String m) {
        JOptionPane.showMessageDialog(this, m, " Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m, "️ Información",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
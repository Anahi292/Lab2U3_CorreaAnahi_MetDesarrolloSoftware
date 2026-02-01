package vista;

import controlador.ControladorCitas;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaAgendarCita extends JFrame {

    private ControladorCitas controlador;
    private JTextField txtNombre, txtEmail, txtTelefono;
    private JComboBox<String> comboHorarios;
    private JButton btnAgendar, btnActualizar, btnVolver;

    private List<LocalDateTime> horariosActuales;

    private DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VistaAgendarCita() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("🗓️ Sistema de Gestión de Citas - Agendar");
        setSize(700, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 242, 245));
    }

    private void inicializarComponentes() {
        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelFormulario(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel lblTitulo = new JLabel(" Agendar Nueva Cita");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Complete el formulario para reservar su cita médica");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSub.setForeground(new Color(236, 240, 241));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblSub);

        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(30, 30, 30, 30),
                new LineBorder(new Color(189, 195, 199), 1, true)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(crearLabel(" Nombre Completo:"), gbc);

        gbc.gridx = 1;
        txtNombre = crearTextField();
        txtNombre.setToolTipText("Ingrese su nombre completo");
        panel.add(txtNombre, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(crearLabel(" Email:"), gbc);

        gbc.gridx = 1;
        txtEmail = crearTextField();
        txtEmail.setToolTipText("ejemplo@correo.com");
        panel.add(txtEmail, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(crearLabel(" Teléfono:"), gbc);

        gbc.gridx = 1;
        txtTelefono = crearTextField();
        txtTelefono.setToolTipText("Ingrese su número de teléfono");
        panel.add(txtTelefono, gbc);

        // Horario
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(crearLabel("? Horario Disponible:"), gbc);

        gbc.gridx = 1;
        comboHorarios = new JComboBox<>();
        comboHorarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboHorarios.setPreferredSize(new Dimension(350, 40));
        comboHorarios.setBackground(Color.WHITE);
        panel.add(comboHorarios, gbc);

        // Panel de información adicional
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 12, 12, 12);
        panel.add(crearPanelInfo(), gbc);

        return panel;
    }

    private JPanel crearPanelInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblInfo = new JLabel("ℹ️ Información Importante");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDetalle1 = new JLabel("• Las citas se agendan con 24 horas de anticipación");
        lblDetalle1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDetalle1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDetalle2 = new JLabel("• Duración aproximada: 30 minutos");
        lblDetalle2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDetalle2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDetalle3 = new JLabel("• Puede cancelar su cita desde 'Mis Citas'");
        lblDetalle3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDetalle3.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblInfo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblDetalle1);
        panel.add(Box.createVerticalStrut(3));
        panel.add(lblDetalle2);
        panel.add(Box.createVerticalStrut(3));
        panel.add(lblDetalle3);

        return panel;
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15));
        l.setForeground(new Color(44, 62, 80));
        return l;
    }

    private JTextField crearTextField() {
        JTextField t = new JTextField();
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setPreferredSize(new Dimension(350, 40));
        t.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(189, 195, 199), 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return t;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setBackground(new Color(240, 242, 245));

        btnVolver = crearBoton(" Volver al Menú", new Color(52, 73, 94));
        btnActualizar = crearBoton(" Actualizar Horarios", new Color(52, 152, 219));
        btnAgendar = crearBoton(" Agendar Cita", new Color(46, 204, 113));

        btnVolver.addActionListener(e -> dispose());
        btnActualizar.addActionListener(e -> {
            controlador.actualizarHorariosDisponibles();
            mostrarMensaje("Horarios actualizados");
        });
        btnAgendar.addActionListener(e -> agendarCita());

        panel.add(btnVolver);
        panel.add(btnActualizar);
        panel.add(btnAgendar);

        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setPreferredSize(new Dimension(210, 45));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(color);
            }
        });

        return b;
    }

    // CORREGIDO: Mejor validación antes de agendar
    private void agendarCita() {
        // Validar que se haya seleccionado un horario
        if (comboHorarios.getSelectedIndex() == -1 || 
            horariosActuales == null || 
            horariosActuales.isEmpty()) {
            mostrarError("Por favor, seleccione un horario disponible");
            return;
        }

        // Obtener datos del formulario
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // Validaciones
        if (nombre.isEmpty()) {
            mostrarError("El nombre es obligatorio");
            txtNombre.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            mostrarError("El email es obligatorio");
            txtEmail.requestFocus();
            return;
        }

        if (!validarEmail(email)) {
            mostrarError("Ingrese un email válido");
            txtEmail.requestFocus();
            return;
        }

        if (telefono.isEmpty()) {
            mostrarError("El teléfono es obligatorio");
            txtTelefono.requestFocus();
            return;
        }

        if (!validarTelefono(telefono)) {
            mostrarError("Ingrese un teléfono válido (solo números)");
            txtTelefono.requestFocus();
            return;
        }

        // Obtener fecha seleccionada
        LocalDateTime fecha = horariosActuales.get(comboHorarios.getSelectedIndex());

        // Confirmar con el usuario
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmar cita para " + nombre + "\n" +
                "Fecha: " + fecha.format(formatter) + "?",
                "Confirmar Cita",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Enviar al controlador
            controlador.agendarCita(nombre, email, telefono, fecha);
        }
    }

    // Validar formato de email
    private boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Validar teléfono (solo números)
    private boolean validarTelefono(String telefono) {
        return telefono.matches("^[0-9]{7,15}$");
    }

    // ================== MÉTODOS QUE USA EL CONTROLADOR ==================

    public void setControlador(ControladorCitas c) {
        this.controlador = c;
    }

    public void actualizarComboHorarios(List<LocalDateTime> horarios) {
        horariosActuales = horarios;
        comboHorarios.removeAllItems();

        if (horarios.isEmpty()) {
            comboHorarios.addItem("No hay horarios disponibles");
            comboHorarios.setEnabled(false);
            btnAgendar.setEnabled(false);
            return;
        }

        comboHorarios.setEnabled(true);
        btnAgendar.setEnabled(true);

        for (LocalDateTime h : horarios) {
            comboHorarios.addItem(h.format(formatter));
        }

        System.out.println(" Horarios cargados: " + horarios.size());
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        if (comboHorarios.getItemCount() > 0) {
            comboHorarios.setSelectedIndex(0);
        }
        txtNombre.requestFocus();
        System.out.println("? Formulario limpiado");
    }

    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m, "️ Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String m) {
        JOptionPane.showMessageDialog(this, m, " Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                " Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
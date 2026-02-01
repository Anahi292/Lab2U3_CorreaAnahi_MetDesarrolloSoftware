package vista;

import controlador.ControladorHorarios;
import modelo.ConfiguracionHorario;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalTime;

public class VistaGestionHorarios extends JFrame {
    private ControladorHorarios controlador;
    private JComboBox<String> comboDias;
    private JSpinner spinnerHoraInicio;
    private JSpinner spinnerHoraFin;
    private JSpinner spinnerDuracion;
    private JSpinner spinnerDescanso;
    private JCheckBox checkActivo;
    private JLabel lblCapacidad;
    private JButton btnGuardar, btnCargar, btnVolver;
    
    private String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    
    public VistaGestionHorarios() {
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle(" Gestión de Horarios de Atención");
        setSize(700, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(236, 240, 241));
    }
    
    private void inicializarComponentes() {
        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelFormulario(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 68, 173));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Configuración de Horarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Define tu disponibilidad semanal");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblSubtitulo);
        
        return panel;
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(25, 25, 25, 25),
            new LineBorder(new Color(189, 195, 199), 1, true)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 15, 12, 15);
        
        // Día de la semana
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(crearLabel(" Día de la Semana:"), gbc);
        gbc.gridx = 1;
        comboDias = new JComboBox<>(dias);
        comboDias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboDias.setPreferredSize(new Dimension(250, 35));
        comboDias.addActionListener(e -> cargarConfiguracionActual());
        panel.add(comboDias, gbc);
        
        // Hora inicio
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(crearLabel(" Hora de Inicio:"), gbc);
        gbc.gridx = 1;
        SpinnerDateModel modelInicio = new SpinnerDateModel();
        spinnerHoraInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm");
        spinnerHoraInicio.setEditor(editorInicio);
        spinnerHoraInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerHoraInicio.setPreferredSize(new Dimension(250, 35));
        panel.add(spinnerHoraInicio, gbc);
        
        // Hora fin
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(crearLabel(" Hora de Fin:"), gbc);
        gbc.gridx = 1;
        spinnerHoraFin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(spinnerHoraFin, "HH:mm");
        spinnerHoraFin.setEditor(editorFin);
        spinnerHoraFin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerHoraFin.setPreferredSize(new Dimension(250, 35));
        panel.add(spinnerHoraFin, gbc);
        
        // Duración de cita
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(crearLabel("️ Duración por Cita (min):"), gbc);
        gbc.gridx = 1;
        spinnerDuracion = new JSpinner(new SpinnerNumberModel(30, 15, 120, 15));
        spinnerDuracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerDuracion.setPreferredSize(new Dimension(250, 35));
        spinnerDuracion.addChangeListener(e -> actualizarCapacidad());
        panel.add(spinnerDuracion, gbc);
        
        // Tiempo de descanso
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(crearLabel(" Tiempo de Descanso (min):"), gbc);
        gbc.gridx = 1;
        spinnerDescanso = new JSpinner(new SpinnerNumberModel(5, 0, 30, 5));
        spinnerDescanso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinnerDescanso.setPreferredSize(new Dimension(250, 35));
        spinnerDescanso.addChangeListener(e -> actualizarCapacidad());
        panel.add(spinnerDescanso, gbc);
        
        // Día activo
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(crearLabel(" Día Activo:"), gbc);
        gbc.gridx = 1;
        checkActivo = new JCheckBox("Atender en este día");
        checkActivo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkActivo.setBackground(Color.WHITE);
        checkActivo.setSelected(true);
        checkActivo.addActionListener(e -> actualizarCapacidad());
        panel.add(checkActivo, gbc);
        
        // Capacidad calculada
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JPanel panelCapacidad = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCapacidad.setBackground(new Color(241, 196, 15));
        panelCapacidad.setBorder(new EmptyBorder(15, 15, 15, 15));
        lblCapacidad = new JLabel("Capacidad: 0 citas por día");
        lblCapacidad.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCapacidad.setForeground(new Color(52, 73, 94));
        panelCapacidad.add(lblCapacidad);
        panel.add(panelCapacidad, gbc);
        
        return panel;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        btnVolver = crearBoton("️ Volver al Menú", new Color(52, 73, 94));
        btnVolver.addActionListener(e -> dispose());
        
        btnCargar = crearBoton(" Cargar Configuración", new Color(52, 152, 219));
        btnCargar.addActionListener(e -> cargarConfiguracionActual());
       
        btnGuardar = crearBoton(" Guardar Configuración", new Color(46, 204, 113));
        btnGuardar.addActionListener(e -> guardarConfiguracion());
        
        panel.add(btnVolver);
        panel.add(btnCargar);
        panel.add(btnGuardar);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }
    
    private void cargarConfiguracionActual() {
        String diaSeleccionado = (String) comboDias.getSelectedItem();
        controlador.cargarConfiguracion(diaSeleccionado);
    }
    
    private void guardarConfiguracion() {
        String dia = (String) comboDias.getSelectedItem();
        
        java.util.Date dateInicio = (java.util.Date) spinnerHoraInicio.getValue();
        java.util.Date dateFin = (java.util.Date) spinnerHoraFin.getValue();
        
        LocalTime inicio = LocalTime.of(dateInicio.getHours(), dateInicio.getMinutes());
        LocalTime fin = LocalTime.of(dateFin.getHours(), dateFin.getMinutes());
        
        if (fin.isBefore(inicio) || fin.equals(inicio)) {
            mostrarError(" La hora de fin debe ser posterior a la hora de inicio");
            return;
        }
        
        int duracion = (Integer) spinnerDuracion.getValue();
        int descanso = (Integer) spinnerDescanso.getValue();
        boolean activo = checkActivo.isSelected();
        
        controlador.guardarConfiguracion(dia, inicio, fin, duracion, descanso, activo);
    }
    
    private void actualizarCapacidad() {
        try {
            java.util.Date dateInicio = (java.util.Date) spinnerHoraInicio.getValue();
            java.util.Date dateFin = (java.util.Date) spinnerHoraFin.getValue();
            
            int minutosDisponibles = (dateFin.getHours() * 60 + dateFin.getMinutes()) -
                                   (dateInicio.getHours() * 60 + dateInicio.getMinutes());
            
            int duracion = (Integer) spinnerDuracion.getValue();
            int descanso = (Integer) spinnerDescanso.getValue();
            int minutosPorCita = duracion + descanso;
            
            int capacidad = checkActivo.isSelected() ? minutosDisponibles / minutosPorCita : 0;
            lblCapacidad.setText("Capacidad: " + capacidad + " citas por día");
        } catch (Exception e) {
            lblCapacidad.setText("Capacidad: -- citas por día");
        }
    }
    
    public void setControlador(ControladorHorarios controlador) {
        this.controlador = controlador;
    }
    
    public void cargarDatos(ConfiguracionHorario config) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        
        cal.set(java.util.Calendar.HOUR_OF_DAY, config.getHoraInicio().getHour());
        cal.set(java.util.Calendar.MINUTE, config.getHoraInicio().getMinute());
        spinnerHoraInicio.setValue(cal.getTime());
        
        cal.set(java.util.Calendar.HOUR_OF_DAY, config.getHoraFin().getHour());
        cal.set(java.util.Calendar.MINUTE, config.getHoraFin().getMinute());
        spinnerHoraFin.setValue(cal.getTime());
        
        spinnerDuracion.setValue(config.getDuracionCita());
        spinnerDescanso.setValue(config.getTiempoDescanso());
        checkActivo.setSelected(config.isActivo());
        
        actualizarCapacidad();
    }
    
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}





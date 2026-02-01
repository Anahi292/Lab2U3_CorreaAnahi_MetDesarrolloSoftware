package vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MockupHistorialCitas extends JFrame {
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> comboFiltroEstado;
    private JTextField txtFechaInicio, txtFechaFin;
    private JButton btnFiltrar, btnLimpiar, btnExportar, btnVolver;
    private JLabel lblTotalCitas, lblAtendidas, lblCanceladas, lblPendientes;
    
    public MockupHistorialCitas() {
        configurarVentana();
        inicializarComponentes();
        cargarDatosSimulados();
    }
    
    private void configurarVentana() {
        setTitle("Historial de Citas - Vista del Usuario");
        setSize(1000, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(236, 240, 241));
    }
    
    private void inicializarComponentes() {
        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelEstadisticas(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Mi Historial de Citas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Consulta todas tus citas pasadas y futuras");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(new Color(41, 128, 185));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTexto.add(lblTitulo);
        panelTexto.add(Box.createVerticalStrut(5));
        panelTexto.add(lblSubtitulo);
        
        panel.add(panelTexto, BorderLayout.WEST);
        
        // Botón volver
        btnVolver = new JButton("⬅️ Volver al Menú");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setBackground(new Color(52, 73, 94));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setPreferredSize(new Dimension(180, 40));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> dispose());
        panel.add(btnVolver, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        panel.add(crearPanelFiltros(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Estado
        JLabel lblEstado = crearLabel("Estado:");
        String[] estados = {"Todos", "Pendiente", "Atendida", "Cancelada", "No Presentada"};
        comboFiltroEstado = new JComboBox<>(estados);
        comboFiltroEstado.setPreferredSize(new Dimension(150, 30));
        comboFiltroEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Fechas
        JLabel lblFechaInicio = crearLabel("Desde:");
        txtFechaInicio = new JTextField("01/01/2026", 10);
        txtFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel lblFechaFin = crearLabel("Hasta:");
        txtFechaFin = new JTextField("31/12/2026", 10);
        txtFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Botones
        btnFiltrar = crearBotonPequeño("Filtrar", new Color(52, 152, 219));
        btnLimpiar = crearBotonPequeño("Limpiar", new Color(149, 165, 166));
        btnExportar = crearBotonPequeño("Exportar PDF", new Color(231, 76, 60));
        
        panel.add(lblEstado);
        panel.add(comboFiltroEstado);
        panel.add(lblFechaInicio);
        panel.add(txtFechaInicio);
        panel.add(lblFechaFin);
        panel.add(txtFechaFin);
        panel.add(btnFiltrar);
        panel.add(btnLimpiar);
        panel.add(btnExportar);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(new Color(189, 195, 199), 1, true));
        
        String[] columnas = {"ID", "Fecha y Hora", "Estado", "Notas", "Acciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaHistorial.setRowHeight(40);
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaHistorial.getTableHeader().setBackground(new Color(52, 73, 94));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.setSelectionBackground(new Color(52, 152, 219));
        tablaHistorial.setSelectionForeground(Color.WHITE);
        
        // Anchos de columnas
        tablaHistorial.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaHistorial.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaHistorial.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaHistorial.getColumnModel().getColumn(3).setPreferredWidth(300);
        tablaHistorial.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        // Renderer para estado
        tablaHistorial.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String estado = value.toString();
                    switch (estado) {
                        case "Atendida":
                            label.setBackground(new Color(46, 204, 113));
                            label.setForeground(Color.WHITE);
                            break;
                        case "Pendiente":
                            label.setBackground(new Color(241, 196, 15));
                            label.setForeground(Color.WHITE);
                            break;
                        case "Cancelada":
                            label.setBackground(new Color(231, 76, 60));
                            label.setForeground(Color.WHITE);
                            break;
                        case "No Presentada":
                            label.setBackground(new Color(149, 165, 166));
                            label.setForeground(Color.WHITE);
                            break;
                    }
                }
                label.setHorizontalAlignment(CENTER);
                label.setOpaque(true);
                return label;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(null);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        panel.add(crearTarjetaEstadistica("Total de Citas", "45", new Color(52, 152, 219)));
        panel.add(crearTarjetaEstadistica("Atendidas", "38", new Color(46, 204, 113)));
        panel.add(crearTarjetaEstadistica("Canceladas", "5", new Color(231, 76, 60)));
        panel.add(crearTarjetaEstadistica("Pendientes", "2", new Color(241, 196, 15)));
        
        return panel;
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(color);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValor.setForeground(Color.WHITE);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblValor);
        
        return panel;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }
    
    private JButton crearBotonPequeño(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Solo efecto visual, sin funcionalidad
        btn.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, 
                "MOCKUP: Esta funcionalidad se implementará en la versión completa",
                "Prototipo No Funcional", 
                JOptionPane.INFORMATION_MESSAGE)
        );
        
        return btn;
    }
    
    private void cargarDatosSimulados() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        Object[][] datos = {
            {1045, "18/01/2026 10:00", "Pendiente", "Revisión general", "Ver detalles"},
            {1044, "15/01/2026 14:30", "Atendida", "Control de seguimiento", "Ver detalles"},
            {1043, "10/01/2026 09:00", "Atendida", "Primera consulta", "Ver detalles"},
            {1042, "05/01/2026 16:00", "Cancelada", "Cancelada por el paciente", "Ver detalles"},
            {1041, "20/12/2025 11:30", "Atendida", "Examen de rutina", "Ver detalles"},
            {1040, "15/12/2025 15:00", "No Presentada", "No se presentó a la cita", "Ver detalles"},
            {1039, "10/12/2025 10:00", "Atendida", "Control mensual", "Ver detalles"},
            {1038, "05/12/2025 14:00", "Atendida", "Consulta de resultados", "Ver detalles"},
        };
        
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }
}
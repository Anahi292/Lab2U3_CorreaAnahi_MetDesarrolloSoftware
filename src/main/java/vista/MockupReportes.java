package vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.table.*;

public class MockupReportes extends JFrame {
    private JComboBox<String> comboPeriodo, comboPersonal;
    private JTextField txtFechaInicio, txtFechaFin;
    private JButton btnGenerar, btnExportarPDF, btnExportarExcel, btnVolver;
    private JPanel panelGraficos;
    private JTable tablaEstadisticas;
    private DefaultTableModel modeloTabla;
    
    public MockupReportes() {
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle(" Panel de Reportes - Administrador");
        setSize(1200, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(236, 240, 241));
    }
    
    private void inicializarComponentes() {
        add(crearPanelTitulo(), BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(236, 240, 241));
        panelCentral.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        panelCentral.add(crearPanelFiltros(), BorderLayout.NORTH);
        panelCentral.add(crearPanelContenido(), BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(new EmptyBorder(25, 20, 25, 20));
        
        JLabel lblTitulo = new JLabel("Dashboard de Reportes y Estadísticas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Análisis detallado del desempeño del sistema de citas");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(new Color(52, 73, 94));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTexto.add(lblTitulo);
        panelTexto.add(Box.createVerticalStrut(8));
        panelTexto.add(lblSubtitulo);
        
        panel.add(panelTexto, BorderLayout.WEST);
        
        // Botón volver
        btnVolver = new JButton("️ Volver al Menú");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setBackground(new Color(149, 165, 166));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setPreferredSize(new Dimension(180, 40));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> dispose());
        panel.add(btnVolver, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 2, true),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        // Período
        JLabel lblPeriodo = crearLabel(" Período:");
        String[] periodos = {"Hoy", "Esta Semana", "Este Mes", "Último Trimestre", 
                            "Este Año", "Personalizado"};
        comboPeriodo = new JComboBox<>(periodos);
        comboPeriodo.setSelectedIndex(2);
        comboPeriodo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboPeriodo.setPreferredSize(new Dimension(150, 32));
        
        // Fechas personalizadas
        JLabel lblDesde = crearLabel("Desde:");
        txtFechaInicio = new JTextField("01/01/2026", 10);
        txtFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFechaInicio.setPreferredSize(new Dimension(100, 32));
        
        JLabel lblHasta = crearLabel("Hasta:");
        txtFechaFin = new JTextField("31/01/2026", 10);
        txtFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFechaFin.setPreferredSize(new Dimension(100, 32));
        
        // Personal
        JLabel lblPersonal = crearLabel(" Personal:");
        String[] personal = {"Todos", "Dr. Juan Pérez", "Dra. María García", 
                            "Dr. Carlos López", "Dra. Ana Martínez"};
        comboPersonal = new JComboBox<>(personal);
        comboPersonal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboPersonal.setPreferredSize(new Dimension(180, 32));
        
        // Botón generar
        btnGenerar = new JButton(" Generar Reporte");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerar.setBackground(new Color(52, 152, 219));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFocusPainted(false);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setPreferredSize(new Dimension(160, 35));
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.addActionListener(e -> mostrarMockupMessage());
        
        panel.add(lblPeriodo);
        panel.add(comboPeriodo);
        panel.add(lblDesde);
        panel.add(txtFechaInicio);
        panel.add(lblHasta);
        panel.add(txtFechaFin);
        panel.add(lblPersonal);
        panel.add(comboPersonal);
        panel.add(btnGenerar);
        
        return panel;
    }
    
    private JPanel crearPanelContenido() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(236, 240, 241));
        
        // Panel superior: Métricas rápidas
        panel.add(crearPanelMetricas(), BorderLayout.NORTH);
        
        // Panel central: División entre gráficos y tabla
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(crearPanelGraficos());
        splitPane.setBottomComponent(crearPanelTabla());
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.5);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelMetricas() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 12, 0));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        panel.add(crearTarjetaMetrica("Total Citas", "1,247", "↑ 12%", 
            new Color(52, 152, 219), new Color(41, 128, 185)));
        panel.add(crearTarjetaMetrica("Atendidas", "1,089", "87.3%", 
            new Color(46, 204, 113), new Color(39, 174, 96)));
        panel.add(crearTarjetaMetrica("Canceladas", "98", "7.9%", 
            new Color(231, 76, 60), new Color(192, 57, 43)));
        panel.add(crearTarjetaMetrica("No Presentadas", "60", "4.8%", 
            new Color(149, 165, 166), new Color(127, 140, 141)));
        panel.add(crearTarjetaMetrica("Tasa Asistencia", "91.4%", "↑ 2.3%", 
            new Color(155, 89, 182), new Color(142, 68, 173)));
        
        return panel;
    }
    
    private JPanel crearTarjetaMetrica(String titulo, String valor, String cambio, 
                                       Color colorFondo, Color colorBorde) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(colorBorde, 3, true),
            new EmptyBorder(20, 15, 20, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(Color.WHITE);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblCambio = new JLabel(cambio);
        lblCambio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCambio.setForeground(new Color(236, 240, 241));
        lblCambio.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblValor);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblCambio);
        
        return panel;
    }
    
    private JPanel crearPanelGraficos() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        panel.add(crearGraficoBarras());
        panel.add(crearGraficoPie());
        
        return panel;
    }
    
    private JPanel crearGraficoBarras() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel("📈 Citas por Mes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(52, 73, 94));
        
        JPanel canvasGrafico = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int[] valores = {85, 92, 78, 105, 98, 120, 110, 95, 88, 102, 115, 108};
                String[] meses = {"E", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"};
                
                int margen = 40;
                int anchoDisponible = width - 2 * margen;
                int altoDisponible = height - 2 * margen;
                int anchoBarra = anchoDisponible / (valores.length * 2);
                int maxValor = 120;
                
                // Dibujar barras
                for (int i = 0; i < valores.length; i++) {
                    int altoBarra = (int) ((double) valores[i] / maxValor * altoDisponible);
                    int x = margen + i * anchoBarra * 2;
                    int y = height - margen - altoBarra;
                    
                    g2.setColor(new Color(52, 152, 219));
                    g2.fillRect(x, y, anchoBarra, altoBarra);
                    
                    g2.setColor(new Color(52, 73, 94));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
                    g2.drawString(meses[i], x + anchoBarra/4, height - margen + 15);
                }
            }
        };
        canvasGrafico.setBackground(Color.WHITE);
        canvasGrafico.setPreferredSize(new Dimension(400, 250));
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(canvasGrafico, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearGraficoPie() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel(" Distribución de Estados");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(52, 73, 94));
        
        JPanel canvasGrafico = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int diameter = Math.min(width, height) - 100;
                int x = (width - diameter) / 2;
                int y = (height - diameter) / 2;
                
                // Datos
                String[] labels = {"Atendidas", "Canceladas", "No Present.", "Pendientes"};
                double[] valores = {87.3, 7.9, 4.8, 0};
                Color[] colores = {
                    new Color(46, 204, 113),
                    new Color(231, 76, 60),
                    new Color(149, 165, 166),
                    new Color(241, 196, 15)
                };
                
                int startAngle = 0;
                for (int i = 0; i < valores.length; i++) {
                    int arcAngle = (int) (valores[i] * 3.6);
                    g2.setColor(colores[i]);
                    g2.fillArc(x, y, diameter, diameter, startAngle, arcAngle);
                    
                    // Borde
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(3));
                    g2.drawArc(x, y, diameter, diameter, startAngle, arcAngle);
                    
                    startAngle += arcAngle;
                }
                
                // Leyenda
                int legendX = x + diameter + 20;
                int legendY = y + 30;
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                
                for (int i = 0; i < labels.length; i++) {
                    g2.setColor(colores[i]);
                    g2.fillRect(legendX, legendY + i * 35, 20, 20);
                    g2.setColor(new Color(52, 73, 94));
                    g2.drawString(labels[i] + ": " + valores[i] + "%", 
                                legendX + 30, legendY + i * 35 + 15);
                }
            }
        };
        canvasGrafico.setBackground(Color.WHITE);
        canvasGrafico.setPreferredSize(new Dimension(400, 250));
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(canvasGrafico, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(
                new LineBorder(new Color(189, 195, 199), 2, true),
                "  Detalle Estadístico por Personal ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        String[] columnas = {"Personal", "Total Citas", "Atendidas", "Canceladas", 
                            "No Present.", "Tasa Asist.", "Promedio/Día"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        Object[][] datos = {
            {"Dr. Juan Pérez", 342, 298, 28, 16, "92.1%", "11.4"},
            {"Dra. María García", 289, 251, 22, 16, "91.6%", "9.6"},
            {"Dr. Carlos López", 315, 278, 24, 13, "92.7%", "10.5"},
            {"Dra. Ana Martínez", 301, 262, 24, 15, "91.4%", "10.0"}
        };
        
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
        
        tablaEstadisticas = new JTable(modeloTabla);
        tablaEstadisticas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaEstadisticas.setRowHeight(35);
        tablaEstadisticas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaEstadisticas.getTableHeader().setBackground(new Color(52, 73, 94));
        tablaEstadisticas.getTableHeader().setForeground(Color.WHITE);
        tablaEstadisticas.setSelectionBackground(new Color(52, 152, 219));
        
        JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        btnExportarPDF = crearBoton(" Exportar a PDF", new Color(231, 76, 60));
        btnExportarExcel = crearBoton(" Exportar a Excel", new Color(39, 174, 96));
        
        panel.add(btnExportarPDF);
        panel.add(btnExportarExcel);
        
        return panel;
    }
    
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        btn.addActionListener(e -> mostrarMockupMessage());
        
        return btn;
    }
    
    private void mostrarMockupMessage() {
        JOptionPane.showMessageDialog(this, 
            "MOCKUP: Esta funcionalidad se implementará en la versión completa\n\n" +
            "En la versión final:\n" +
            "• Los gráficos se generarán con datos reales de la base de datos\n" +
            "• Los reportes se exportarán en formatos PDF y Excel\n" +
            "• Se incluirán más métricas y análisis avanzados\n" +
            "• Los filtros aplicarán cambios dinámicos en tiempo real",
            "Prototipo No Funcional", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
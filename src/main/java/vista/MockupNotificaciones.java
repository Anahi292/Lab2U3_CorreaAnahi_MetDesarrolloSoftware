package vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MockupNotificaciones extends JFrame {
    private JCheckBox chkConfirmacion, chkRecordatorio, chkCancelacion, chkReprogramacion;
    private JComboBox<String> comboRecordatorio;
    private JTextArea txtPreviewEmail;
    private JButton btnGuardar, btnProbar, btnRestaurar, btnVolver;
    
    public MockupNotificaciones() {
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle(" Configuración de Notificaciones");
        setSize(900, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(236, 240, 241));
    }
    
    private void inicializarComponentes() {
        add(crearPanelTitulo(), BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setBackground(new Color(236, 240, 241));
        panelCentral.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        panelCentral.add(crearPanelConfiguracion());
        panelCentral.add(crearPanelVistaPrev());
        
        add(panelCentral, BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 126, 34));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Configuración de Notificaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Personaliza cómo y cuándo recibes tus notificaciones");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(new Color(230, 126, 34));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTexto.add(lblTitulo);
        panelTexto.add(Box.createVerticalStrut(5));
        panelTexto.add(lblSubtitulo);
        
        panel.add(panelTexto, BorderLayout.WEST);
        
        // Botón volver
        btnVolver = new JButton("️ Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setBackground(new Color(52, 73, 94));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setPreferredSize(new Dimension(140, 40));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> dispose());
        panel.add(btnVolver, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel crearPanelConfiguracion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(
                new LineBorder(new Color(189, 195, 199), 2, true),
                " ️ Preferencias de Notificación ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Sección: Tipos de notificaciones
        JPanel seccionTipos = crearSeccion("📧 Tipos de Notificaciones por Email");
        
        chkConfirmacion = crearCheckBox("✅ Confirmación de Cita Agendada", true);
        chkRecordatorio = crearCheckBox("⏰ Recordatorio de Cita Próxima", true);
        chkCancelacion = crearCheckBox("❌ Notificación de Cancelación", true);
        chkReprogramacion = crearCheckBox("🔄 Notificación de Reprogramación", true);
        
        seccionTipos.add(chkConfirmacion);
        seccionTipos.add(Box.createVerticalStrut(10));
        seccionTipos.add(chkRecordatorio);
        seccionTipos.add(Box.createVerticalStrut(10));
        seccionTipos.add(chkCancelacion);
        seccionTipos.add(Box.createVerticalStrut(10));
        seccionTipos.add(chkReprogramacion);
        
        // Sección: Tiempo de recordatorio
        JPanel seccionRecordatorio = crearSeccion("⏱️ Tiempo de Recordatorio");
        
        JLabel lblTiempo = new JLabel("Enviar recordatorio:");
        lblTiempo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTiempo.setForeground(new Color(52, 73, 94));
        
        String[] opciones = {
            "30 minutos antes",
            "1 hora antes",
            "2 horas antes",
            "12 horas antes",
            "24 horas antes (Recomendado)",
            "48 horas antes"
        };
        comboRecordatorio = new JComboBox<>(opciones);
        comboRecordatorio.setSelectedIndex(4);
        comboRecordatorio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboRecordatorio.setMaximumSize(new Dimension(300, 30));
        comboRecordatorio.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        seccionRecordatorio.add(lblTiempo);
        seccionRecordatorio.add(Box.createVerticalStrut(8));
        seccionRecordatorio.add(comboRecordatorio);
        
        // Sección: Información adicional
        JPanel seccionInfo = crearPanelInfo();
        
        panel.add(seccionTipos);
        panel.add(Box.createVerticalStrut(20));
        panel.add(seccionRecordatorio);
        panel.add(Box.createVerticalStrut(20));
        panel.add(seccionInfo);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(41, 128, 185));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(12));
        
        return panel;
    }
    
    private JCheckBox crearCheckBox(String texto, boolean seleccionado) {
        JCheckBox check = new JCheckBox(texto);
        check.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        check.setBackground(Color.WHITE);
        check.setSelected(seleccionado);
        check.setFocusPainted(false);
        check.setAlignmentX(Component.LEFT_ALIGNMENT);
        check.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return check;
    }
    
    private JPanel crearPanelInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(174, 214, 241));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(41, 128, 185), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(" Información");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(21, 67, 96));
        
        JTextArea txtInfo = new JTextArea(
            "• Las notificaciones se envían a tu email registrado\n" +
            "• Puedes cambiar estas preferencias en cualquier momento\n" +
            "• Los recordatorios te ayudan a no olvidar tus citas\n" +
            "• Todas las notificaciones incluyen un enlace para gestionar tu cita"
        );
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtInfo.setForeground(new Color(21, 67, 96));
        txtInfo.setBackground(new Color(174, 214, 241));
        txtInfo.setEditable(false);
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setBorder(null);
        
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(txtInfo);
        
        return panel;
    }
    
    private JPanel crearPanelVistaPrev() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(
                new LineBorder(new Color(189, 195, 199), 2, true),
                " 📧 Vista Previa del Email ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(52, 73, 94)
            ),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTipoEmail = new JLabel("Tipo: Confirmación de Cita");
        lblTipoEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTipoEmail.setForeground(new Color(46, 204, 113));
        
        txtPreviewEmail = new JTextArea();
        txtPreviewEmail.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtPreviewEmail.setEditable(false);
        txtPreviewEmail.setLineWrap(true);
        txtPreviewEmail.setWrapStyleWord(true);
        txtPreviewEmail.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtPreviewEmail.setText(generarPreviewEmail());
        
        JScrollPane scrollPane = new JScrollPane(txtPreviewEmail);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        
        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTipo.setBackground(Color.WHITE);
        panelTipo.add(lblTipoEmail);
        
        JComboBox<String> comboTipoPreview = new JComboBox<>(new String[]{
            "Confirmación de Cita",
            "Recordatorio 24h",
            "Notificación de Cancelación",
            "Notificación de Reprogramación"
        });
        comboTipoPreview.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelTipo.add(Box.createHorizontalStrut(10));
        panelTipo.add(comboTipoPreview);
        
        panel.add(panelTipo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private String generarPreviewEmail() {
        return "═══════════════════════════════════════════════════\n" +
               "  SISTEMA DE GESTIÓN DE CITAS\n" +
               "═══════════════════════════════════════════════════\n\n" +
               "Estimado/a Usuario,\n\n" +
               "Su cita ha sido CONFIRMADA exitosamente.\n\n" +
               "DETALLES DE LA CITA:\n" +
               "───────────────────────────────────────────────────\n" +
               "Número de Referencia: #1045\n" +
               "Fecha: Viernes, 24 de Enero de 2026\n" +
               "Hora: 10:00 AM\n" +
               "Estado: Confirmada\n\n" +
               "OPCIONES DISPONIBLES:\n" +
               "───────────────────────────────────────────────────\n" +
               "• Puede cancelar su cita hasta 2 horas antes\n" +
               "• Puede reprogramar su cita en cualquier momento\n" +
               "• Recibirá un recordatorio 24 horas antes\n\n" +
               "Para gestionar su cita, haga clic aquí:\n" +
               "https://sistema-citas.com/mi-cita/1045\n\n" +
               "IMPORTANTE:\n" +
               "Si no puede asistir, por favor cancele su cita con\n" +
               "anticipación para que otros usuarios puedan aprovechar\n" +
               "ese horario.\n\n" +
               "Gracias por utilizar nuestro sistema.\n\n" +
               "───────────────────────────────────────────────────\n" +
               "Este es un mensaje automático, por favor no responda.\n" +
               "═══════════════════════════════════════════════════";
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        btnRestaurar = crearBoton(" Restaurar Defaults", new Color(149, 165, 166));
        btnProbar = crearBoton(" Enviar Email de Prueba", new Color(52, 152, 219));
        btnGuardar = crearBoton(" Guardar Configuración", new Color(46, 204, 113));
        
        panel.add(btnRestaurar);
        panel.add(btnProbar);
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
        
        // Solo efecto visual, sin funcionalidad
        btn.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, 
                "MOCKUP: Esta funcionalidad se implementará en la versión completa\n\n" +
                "En la versión final:\n" +
                "• Se guardará la configuración en la base de datos\n" +
                "• Se enviarán emails reales según las preferencias\n" +
                "• Se podrá probar el envío de notificaciones",
                "Prototipo No Funcional", 
                JOptionPane.INFORMATION_MESSAGE)
        );
        
        return btn;
    }
}
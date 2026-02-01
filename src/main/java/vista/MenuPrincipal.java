package vista;

import modelo.*;
import controlador.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * MENÚ PRINCIPAL - DASHBOARD
 * SISTEMA DE GESTIÓN DE CITAS
 */
public class MenuPrincipal extends JFrame {

    private GestorCitas gestorCitas;
    private GestorHorarios gestorHorarios;

    public MenuPrincipal() {
        gestorCitas = new GestorCitas();
        gestorHorarios = new GestorHorarios();

        configurarVentana();
        cargarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Citas");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
    }

    private void cargarComponentes() {
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearDashboard(), BorderLayout.CENTER);
    }

    // ================== ENCABEZADO ==================

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 44, 62));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("Sistema de Gestión de Citas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel subtitulo = new JLabel("Panel principal del sistema");
        subtitulo.setForeground(new Color(200, 200, 200));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(subtitulo);

        panel.add(textos, BorderLayout.WEST);

        return panel;
    }

    // ================== DASHBOARD ==================

    private JPanel crearDashboard() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 25, 25));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 247, 250));

        panel.add(crearTarjeta("Agendar Nueva Cita", new Color(93, 173, 226), this::abrirAgendarCita));
        panel.add(crearTarjeta("Mis Citas Agendadas", new Color(243, 156, 18), this::abrirCancelarCita));
        panel.add(crearTarjeta("Configuración de Horarios", new Color(155, 89, 182), this::abrirGestionHorarios));
        panel.add(crearTarjeta("Mi Historial de Citas", new Color(52, 152, 219), this::abrirHistorialCitas));
        panel.add(crearTarjeta("Configuración de Notificaciones", new Color(248, 196, 113), this::abrirNotificaciones));
        panel.add(crearTarjeta("Dashboard de Reportes y Estadísticas", new Color(86, 101, 115), this::abrirReportes));

        return panel;
    }

    // ================== TARJETA ==================

    private JPanel crearTarjeta(String titulo, Color color, Runnable accion) {

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(new CompoundBorder(
                new LineBorder(color, 2, true),
                new EmptyBorder(30, 30, 30, 30)
        ));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(44, 62, 80));

        JButton boton = new JButton("ABRIR");
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(150, 40));

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(lblTitulo, BorderLayout.CENTER);

        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(boton);

        tarjeta.add(centro, BorderLayout.CENTER);
        tarjeta.add(sur, BorderLayout.SOUTH);

        boton.addActionListener(e -> accion.run());

        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                tarjeta.setBackground(new Color(250, 250, 250));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                tarjeta.setBackground(Color.WHITE);
            }

            public void mouseClicked(java.awt.event.MouseEvent e) {
                accion.run();
            }
        });

        return tarjeta;
    }

    // ================== ACCIONES ==================

    private void abrirAgendarCita() {
        VistaAgendarCita vista = new VistaAgendarCita();
        ControladorCitas c = new ControladorCitas(gestorCitas, vista);
        c.iniciar();
    }

    private void abrirCancelarCita() {
        VistaCancelarCita vista = new VistaCancelarCita();
        ControladorCancelarCita c = new ControladorCancelarCita(gestorCitas, vista);
        c.iniciar();
    }

    private void abrirGestionHorarios() {
        VistaGestionHorarios vista = new VistaGestionHorarios();
        ControladorHorarios c = new ControladorHorarios(gestorHorarios, vista);
        c.iniciar();
    }

    private void abrirHistorialCitas() {
        new MockupHistorialCitas().setVisible(true);
    }

    private void abrirNotificaciones() {
        new MockupNotificaciones().setVisible(true);
    }

    private void abrirReportes() {
        new MockupReportes().setVisible(true);
    }
}

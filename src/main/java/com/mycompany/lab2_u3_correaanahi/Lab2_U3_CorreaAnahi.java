/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab2_u3_correaanahi;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.MenuPrincipal;

/**
 * Clase Principal del Sistema de Gestión de Citas
 * @author Anahí Correa
 */
public class Lab2_U3_CorreaAnahi {

    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
        
        System.out.println("=================================");
        System.out.println("Sistema de Gestión de Citas - INICIADO");
        System.out.println("=================================");
    }
}
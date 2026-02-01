/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.ConfiguracionHorario;
import modelo.GestorHorarios;
import vista.VistaGestionHorarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ControladorHorariosTest {

    private GestorHorarios gestor;
    private VistaGestionHorariosFake vista;
    private ControladorHorarios controlador;

    // =====================================
    // VISTA FALSA
    // =====================================
    class VistaGestionHorariosFake extends VistaGestionHorarios {

        ConfiguracionHorario ultimaConfig;
        String ultimoMensaje;

        @Override
        public void cargarDatos(ConfiguracionHorario config) {
            ultimaConfig = config;
        }

        @Override
        public void mostrarExito(String msg) {
            ultimoMensaje = msg;
        }

        @Override
        public void setVisible(boolean visible) {
            // No hacer nada
        }
    }

    @BeforeEach
    void setUp() {
        gestor = new GestorHorarios();
        vista = new VistaGestionHorariosFake();
        controlador = new ControladorHorarios(gestor, vista);
    }

    // =====================================
    // INICIAR CARGA LUNES
    // =====================================
    @Test
    void iniciar_debeCargarLunes() {

        controlador.iniciar();

        assertNotNull(vista.ultimaConfig);
        assertEquals("Lunes", vista.ultimaConfig.getDiaSemana());
    }

    // =====================================
    // CARGAR DIA ESPECIFICO
    // =====================================
    @Test
    void cargarConfiguracion_martes() {

        controlador.cargarConfiguracion("Martes");

        assertNotNull(vista.ultimaConfig);
        assertEquals("Martes", vista.ultimaConfig.getDiaSemana());
    }

    // =====================================
    // GUARDAR CONFIGURACION
    // =====================================
    @Test
    void guardarConfiguracion_correcto() {

        controlador.guardarConfiguracion(
                "Viernes",
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                20,
                5,
                true
        );

        assertNotNull(vista.ultimoMensaje);
        assertTrue(vista.ultimoMensaje.contains("guardada"));

        ConfiguracionHorario config = gestor.getConfiguracion("Viernes");
        assertEquals(LocalTime.of(8,0), config.getHoraInicio());
        assertEquals(20, config.getDuracionCita());
        assertTrue(config.isActivo());
    }
}


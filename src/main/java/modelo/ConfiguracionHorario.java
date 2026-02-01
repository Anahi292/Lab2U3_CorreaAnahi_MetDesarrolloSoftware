/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracionHorario {

    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private int duracionCita; // en minutos
    private int tiempoDescanso; // en minutos
    private boolean activo;

    public ConfiguracionHorario(String diaSemana) {
        this.diaSemana = diaSemana;
        this.horaInicio = LocalTime.of(9, 0);
        this.horaFin = LocalTime.of(17, 0);
        this.duracionCita = 30;
        this.tiempoDescanso = 5;
        this.activo = true;
    }

    // Getters y Setters
    public String getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getDuracionCita() {
        return duracionCita;
    }

    public void setDuracionCita(int duracionCita) {
        this.duracionCita = duracionCita;
    }

    public int getTiempoDescanso() {
        return tiempoDescanso;
    }

    public void setTiempoDescanso(int tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int calcularCitasPorDia() {
        if (!activo) {
            return 0;
        }
        int minutosDisponibles = (horaFin.getHour() * 60 + horaFin.getMinute())
                - (horaInicio.getHour() * 60 + horaInicio.getMinute());
        int minutosPorCita = duracionCita + tiempoDescanso;
        return minutosDisponibles / minutosPorCita;
    }
}

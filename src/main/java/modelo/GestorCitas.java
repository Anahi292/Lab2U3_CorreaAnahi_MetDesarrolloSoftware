package modelo;

import dao.CitaDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorCitas {
    private List<Cita> citas;
    private List<LocalDateTime> horariosBase;
    private CitaDAO dao;

    public GestorCitas() {
        dao = new CitaDAO();
        horariosBase = new ArrayList<>();
        inicializarHorarios();
        cargarCitasDesdeDB(); // CORREGIDO: Cargar citas al iniciar
    }

    // NUEVO: Cargar citas desde MongoDB
    private void cargarCitasDesdeDB() {
        citas = dao.listar();
        System.out.println(" Citas cargadas en memoria: " + citas.size());
    }

    private void inicializarHorarios() {
        LocalDateTime inicio = LocalDateTime.now()
                .plusDays(1)
                .withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        for (int i = 0; i < 10; i++) {
            horariosBase.add(inicio.plusMinutes(i * 30));
        }
    }

    public List<LocalDateTime> getHorariosDisponibles() {
        // CORREGIDO: Actualizar citas antes de verificar disponibilidad
        cargarCitasDesdeDB();
        
        List<LocalDateTime> disponibles = new ArrayList<>();
        for (LocalDateTime h : horariosBase) {
            if (esDisponible(h)) {
                disponibles.add(h);
            }
        }
        return disponibles;
    }

    // CORREGIDO: Guardar en BD y actualizar memoria
    public boolean agendarCita(String nombre, String email, String telefono, LocalDateTime fecha) {
        // Verificar si el horario está disponible
        if (!esDisponible(fecha)) {
            System.err.println(" Horario no disponible: " + fecha);
            return false;
        }

        // Crear y guardar la cita
        Cita c = new Cita(nombre, email, telefono, fecha);
        
        try {
            dao.guardar(c);
            // Recargar citas desde la BD para mantener sincronización
            cargarCitasDesdeDB();
            System.out.println(" Cita agendada exitosamente");
            return true;
        } catch (Exception e) {
            System.err.println(" Error al guardar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean esDisponible(LocalDateTime fecha) {
        for (Cita c : citas) {
            // Solo considerar citas activas (no canceladas)
            if (c.getFechaHora().equals(fecha) && 
                !c.getEstado().equalsIgnoreCase("Cancelada")) {
                return false;
            }
        }
        return true;
    }

    // CORREGIDO: Obtener citas actualizadas desde BD
    public List<Cita> getCitas() {
        cargarCitasDesdeDB(); // Siempre obtener datos frescos
        return new ArrayList<>(citas); // Retornar copia para evitar modificaciones
    }

    // CORREGIDO: Cancelar y actualizar memoria
    public boolean cancelarCita(int id) {
        boolean exito = dao.cancelar(id);
        if (exito) {
            cargarCitasDesdeDB(); // Actualizar memoria
        }
        return exito;
    }

    // CORREGIDO: Eliminar y actualizar memoria
    public boolean eliminarCita(int id) {
        boolean exito = dao.eliminar(id);
        if (exito) {
            cargarCitasDesdeDB(); // Actualizar memoria
        }
        return exito;
    }

    // Métodos adicionales útiles
    public Cita buscarCitaPorId(int id) {
        for (Cita c : citas) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Cita> getCitasActivas() {
        List<Cita> activas = new ArrayList<>();
        for (Cita c : getCitas()) {
            if (!c.getEstado().equalsIgnoreCase("Cancelada")) {
                activas.add(c);
            }
        }
        return activas;
    }

    public int contarCitas() {
        return getCitas().size();
    }
}
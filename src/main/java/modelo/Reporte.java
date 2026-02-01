package modelo;

public class Reporte {

    private String titulo;
    private int total;

    public Reporte(String titulo, int total) {
        this.titulo = titulo;
        this.total = total;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getTotal() {
        return total;
    }
}

public abstract class OrdenServicio {
    public enum Zona { METROPOLITANA, PERIFERICA }
    public enum EstadoOrden { PENDIENTE, EN_ATENCION, COMPLETADA, CANCELADA }

    private String codigo;
    private String nombreCliente;
    private String direccion;
    private double horasEstimadas;
    private Zona zona;
    private EstadoOrden estado;
    private int visitasRealizadas;

    public OrdenServicio(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona) {
        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = EstadoOrden.PENDIENTE;
        this.visitasRealizadas = 0;
    }

    public abstract double calcularCostoBase();
    public abstract int obtenerTiempoAtencionDias();
    public abstract int obtenerMaxVisitas();
    public abstract String getNombreCategoria();

    public String getCodigo() { return codigo; }
    public String getNombreCliente() { return nombreCliente; }
    public String getDireccion() { return direccion; }
    public double getHorasEstimadas() { return horasEstimadas; }
    public Zona getZona() { return zona; }
    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }
    public int getVisitasRealizadas() { return visitasRealizadas; }
    public void incrementarVisitas() { this.visitasRealizadas++; }
}

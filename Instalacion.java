public class Instalacion extends OrdenServicio {
    private int puntosInstalacion;

    public Instalacion(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona, int puntosInstalacion) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (puntosInstalacion <= 0) throw new IllegalArgumentException("La cantidad de puntos debe ser un entero positivo.");
        this.puntosInstalacion = puntosInstalacion;
    }

    @Override public double calcularCostoBase() {
        double tarifaBase = getZona() == Zona.METROPOLITANA ? 100.0 : 175.0;
        double horasPorPuntos = puntosInstalacion * 0.75;
        double horasFacturables = Math.ceil(Math.max(getHorasEstimadas(), horasPorPuntos));
        return tarifaBase + (90.0 * horasFacturables);
    }
    @Override public int obtenerTiempoAtencionDias() { return getZona() == Zona.METROPOLITANA ? 3 : 5; }
    @Override public int obtenerMaxVisitas() { return 3; }
    @Override public String getNombreCategoria() { return "Instalacion"; }
    public int getPuntosInstalacion() { return puntosInstalacion; }
}

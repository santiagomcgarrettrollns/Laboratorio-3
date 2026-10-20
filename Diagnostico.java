public class Diagnostico extends OrdenServicio {
    public Diagnostico(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (horasEstimadas > 1.5) {
            throw new IllegalArgumentException("Un diagnostico no puede superar 1.5 horas.");
        }
    }

    @Override public double calcularCostoBase() { return getZona() == Zona.METROPOLITANA ? 150.0 : 225.0; }
    @Override public int obtenerTiempoAtencionDias() { return getZona() == Zona.METROPOLITANA ? 1 : 2; }
    @Override public int obtenerMaxVisitas() { return 3; }
    @Override public String getNombreCategoria() { return "Diagnostico"; }
}

public class MantenimientoAireAcondicionado extends OrdenServicio {
    private int btu;

    public MantenimientoAireAcondicionado(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona, int btu) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona);
        if (zona != Zona.METROPOLITANA) throw new IllegalArgumentException("Solo se aceptan mantenimientos de aire acondicionado en zona metropolitana.");
        if (btu < 9000 || btu > 60000) throw new IllegalArgumentException("Los BTU deben estar entre 9,000 y 60,000.");
        this.btu = btu;
    }

    @Override public double calcularCostoBase() { return 100.0 + 110.0 * getHorasEstimadas() + 80.0; }
    @Override public int obtenerTiempoAtencionDias() { return 2; }
    @Override public int obtenerMaxVisitas() { return 1; }
    @Override public String getNombreCategoria() { return "Mantenimiento Aire Acondicionado"; }
}

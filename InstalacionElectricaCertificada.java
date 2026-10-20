public class InstalacionElectricaCertificada extends Instalacion {
    private double valorMateriales;

    public InstalacionElectricaCertificada(String codigo, String nombreCliente, String direccion, double horasEstimadas, Zona zona, int puntosInstalacion, double valorMateriales) {
        super(codigo, nombreCliente, direccion, horasEstimadas, zona, puntosInstalacion);
        this.valorMateriales = valorMateriales;
    }

    @Override public double calcularCostoBase() { return super.calcularCostoBase() + 120.0 + valorMateriales * 0.05; }
    @Override public int obtenerTiempoAtencionDias() { return super.obtenerTiempoAtencionDias() + 2; }
    @Override public int obtenerMaxVisitas() { return 2; }
    @Override public String getNombreCategoria() { return "Instalacion Electrica Certificada"; }
}

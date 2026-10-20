import java.util.ArrayList;
import java.util.List;

public class Jornada {
    public enum EstadoJornada { EN_PREPARACION, EN_ATENCION, CERRADA }
    private Cuadrilla cuadrilla;
    private List<OrdenServicio> ordenesAsignadas;
    private EstadoJornada estado;

    public Jornada(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
        this.ordenesAsignadas = new ArrayList<>();
        this.estado = EstadoJornada.EN_PREPARACION;
    }
    public boolean agregarOrden(OrdenServicio orden) {
        if (estado != EstadoJornada.EN_PREPARACION) return false;
        if (orden instanceof MantenimientoAireAcondicionado && !cuadrilla.isCertificadaRefrigeracion()) return false;
        double horasActuales = ordenesAsignadas.stream().mapToDouble(OrdenServicio::getHorasEstimadas).sum();
        if (horasActuales + orden.getHorasEstimadas() > cuadrilla.getCapacidadHoras()) return false;
        ordenesAsignadas.add(orden);
        return true;
    }
    public void retirarOrden(OrdenServicio orden) { if (estado == EstadoJornada.EN_PREPARACION) ordenesAsignadas.remove(orden); }
    public void iniciarJornada() {
        if (!ordenesAsignadas.isEmpty() && estado == EstadoJornada.EN_PREPARACION) {
            estado = EstadoJornada.EN_ATENCION;
            for (OrdenServicio orden : ordenesAsignadas) orden.setEstado(OrdenServicio.EstadoOrden.EN_ATENCION);
        }
    }
    public Cuadrilla getCuadrilla() { return cuadrilla; }
    public List<OrdenServicio> getOrdenesAsignadas() { return ordenesAsignadas; }
    public EstadoJornada getEstado() { return estado; }
    public void setEstado(EstadoJornada estado) { this.estado = estado; }
}

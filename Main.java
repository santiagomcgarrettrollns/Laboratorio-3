import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<OrdenServicio> listaOrdenes = new ArrayList<>();
    private static List<Cuadrilla> listaCuadrillas = new ArrayList<>();
    private static List<Jornada> listaJornadas = new ArrayList<>();
    private static double ingresoTotalRegistro = 0.0;
    private static double ingresoTotalVisitasAdicionales = 0.0;

    public static void main(String[] args) {
        inicializarDatosBase();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            System.out.println("\n--- TECNIHOGAR - SISTEMA DE GESTION ---");
            System.out.println("1. Cotizar orden (Sin registrar)");
            System.out.println("2. Registrar orden nueva (Cobra el monto completo)");
            System.out.println("3. Registrar nueva cuadrilla");
            System.out.println("4. Crear jornada y asignar ordenes");
            System.out.println("5. Iniciar jornada");
            System.out.println("6. Registrar resultado de visita de una orden");
            System.out.println("7. Consultar ordenes en el sistema");
            System.out.println("8. Generar reportes gerenciales");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opcion: ");
            try { opcion = Integer.parseInt(scanner.nextLine()); publicarOpcion(opcion, scanner); }
            catch (NumberFormatException e) { System.out.println("Error: Ingrese un numero valido."); }
            catch (Exception e) { System.out.println("Error operacional: " + e.getMessage()); }
        } while (opcion != 9);
        scanner.close();
    }

    private static void publicarOpcion(int opcion, Scanner scanner) {
        switch (opcion) {
            case 1: cotizarOrden(scanner); break;
            case 2: registrarOrden(scanner); break;
            case 3: registrarCuadrilla(scanner); break;
            case 4: gestionarJornadas(scanner); break;
            case 5: iniciarJornada(scanner); break;
            case 6: registrarVisita(scanner); break;
            case 7: consultarGeneral(); break;
            case 8: mostrarReportes(); break;
            case 9: System.out.println("Saliendo del sistema..."); break;
            default: System.out.println("Opcion no valida.");
        }
    }

    private static void inicializarDatosBase() {
        listaCuadrillas.add(new Cuadrilla("C001", 8.0, true));
        listaCuadrillas.add(new Cuadrilla("C002", 6.5, false));
        listaCuadrillas.add(new Cuadrilla("C003", 7.0, false));
        try {
            listaOrdenes.add(new Diagnostico("D001", "Ana Perez", "Zona 10", 1.0, OrdenServicio.Zona.METROPOLITANA));
            listaOrdenes.add(new Diagnostico("D002", "Carlos Gomez", "Mixco", 1.5, OrdenServicio.Zona.PERIFERICA));
            listaOrdenes.add(new Instalacion("I001", "Lucia Morales", "Zona 15", 2.0, OrdenServicio.Zona.METROPOLITANA, 4));
            listaOrdenes.add(new Instalacion("I002", "Jorge Ruiz", "Carretera a El Salvador", 3.0, OrdenServicio.Zona.PERIFERICA, 6));
            listaOrdenes.add(new InstalacionElectricaCertificada("IEC001", "Sofia Castro", "Zona 14", 2.5, OrdenServicio.Zona.METROPOLITANA, 5, 2000.0));
            listaOrdenes.add(new InstalacionElectricaCertificada("IEC002", "Mario Lopez", "San Jose Pinula", 2.0, OrdenServicio.Zona.PERIFERICA, 3, 1500.0));
            listaOrdenes.add(new MantenimientoAireAcondicionado("MAC001", "Empresa X", "Zona 9", 2.0, OrdenServicio.Zona.METROPOLITANA, 12000));
            listaOrdenes.add(new MantenimientoAireAcondicionado("MAC002", "Oficina Y", "Zona 4", 1.5, OrdenServicio.Zona.METROPOLITANA, 24000));
        } catch (Exception e) { System.out.println("Error al cargar datos base: " + e.getMessage()); }
    }

    private static void cotizarOrden(Scanner scanner) {
        System.out.println("\n--- COTIZAR ORDEN ---");
        System.out.println("Categorias: 1. Diagnostico | 2. Instalacion | 3. Instalacion Electrica Certificada | 4. Mantenimiento A/C");
        System.out.print("Seleccione categoria: "); int cat = Integer.parseInt(scanner.nextLine());
        System.out.print("Zona (1. Metropolitana, 2. Periferica): "); int z = Integer.parseInt(scanner.nextLine());
        OrdenServicio.Zona zona = z == 1 ? OrdenServicio.Zona.METROPOLITANA : OrdenServicio.Zona.PERIFERICA;
        System.out.print("Horas estimadas: "); double horas = Double.parseDouble(scanner.nextLine());
        try {
            OrdenServicio temp = crearOrden(scanner, cat, "TEMP", "C", "D", horas, zona);
            if (temp != null) {
                System.out.println("\n[Cotizacion Exitosa]");
                System.out.println("Costo total: Q" + String.format("%.2f", temp.calcularCostoBase()));
                System.out.println("Tiempo de atencion: " + temp.obtenerTiempoAtencionDias() + " dias habiles.");
            }
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static OrdenServicio crearOrden(Scanner scanner, int cat, String codigo, String cliente, String direccion, double horas, OrdenServicio.Zona zona) {
        if (cat == 1) return new Diagnostico(codigo, cliente, direccion, horas, zona);
        if (cat == 2) { System.out.print("Puntos a instalar: "); return new Instalacion(codigo, cliente, direccion, horas, zona, Integer.parseInt(scanner.nextLine())); }
        if (cat == 3) { System.out.print("Puntos a instalar: "); int puntos = Integer.parseInt(scanner.nextLine()); System.out.print("Valor de materiales (Q): "); return new InstalacionElectricaCertificada(codigo, cliente, direccion, horas, zona, puntos, Double.parseDouble(scanner.nextLine())); }
        if (cat == 4) { System.out.print("Capacidad en BTU: "); return new MantenimientoAireAcondicionado(codigo, cliente, direccion, horas, zona, Integer.parseInt(scanner.nextLine())); }
        return null;
    }

    private static void registrarOrden(Scanner scanner) {
        System.out.println("\n--- REGISTRAR ORDEN ---");
        System.out.print("Codigo unico: "); String codigo = scanner.nextLine();
        for (OrdenServicio o : listaOrdenes) if (o.getCodigo().equalsIgnoreCase(codigo)) { System.out.println("Error: Ya existe una orden con ese codigo."); return; }
        System.out.print("Nombre cliente: "); String cliente = scanner.nextLine();
        System.out.print("Direccion: "); String direccion = scanner.nextLine();
        System.out.print("Horas estimadas: "); double horas = Double.parseDouble(scanner.nextLine());
        System.out.print("Zona (1. Metro, 2. Periferica): "); int z = Integer.parseInt(scanner.nextLine());
        OrdenServicio.Zona zona = z == 1 ? OrdenServicio.Zona.METROPOLITANA : OrdenServicio.Zona.PERIFERICA;
        System.out.println("Categorias: 1. Diagnostico | 2. Instalacion | 3. Instalacion Electrica Certificada | 4. Mantenimiento A/C");
        int cat = Integer.parseInt(scanner.nextLine());
        try { OrdenServicio nueva = crearOrden(scanner, cat, codigo, cliente, direccion, horas, zona); if (nueva != null) { double costo = nueva.calcularCostoBase(); listaOrdenes.add(nueva); ingresoTotalRegistro += costo; System.out.println("Registrada con exito. Se cobro completo: Q" + String.format("%.2f", costo)); } }
        catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void registrarCuadrilla(Scanner scanner) { System.out.print("Codigo de cuadrilla: "); String cod = scanner.nextLine(); System.out.print("Capacidad maxima de horas: "); double cap = Double.parseDouble(scanner.nextLine()); System.out.print("Certificada en refrigeracion? (s/n): "); boolean cert = scanner.nextLine().equalsIgnoreCase("s"); listaCuadrillas.add(new Cuadrilla(cod, cap, cert)); System.out.println("Cuadrilla registrada."); }

    private static void gestionarJornadas(Scanner scanner) {
        System.out.println("\n1. Crear jornada | 2. Asignar orden a jornada en preparacion"); int op = Integer.parseInt(scanner.nextLine());
        if (op == 1) { System.out.print("Codigo de cuadrilla: "); String codC = scanner.nextLine(); for (Cuadrilla c : listaCuadrillas) if (c.getCodigo().equalsIgnoreCase(codC)) { listaJornadas.add(new Jornada(c)); System.out.println("Jornada creada."); return; } System.out.println("Cuadrilla no encontrada."); }
        else if (op == 2) { Jornada jPrep = null; for (Jornada j : listaJornadas) if (j.getEstado() == Jornada.EstadoJornada.EN_PREPARACION) { jPrep = j; break; } if (jPrep == null) { System.out.println("No hay jornadas en preparacion."); return; } System.out.print("Codigo de orden pendiente: "); String codO = scanner.nextLine(); for (OrdenServicio o : listaOrdenes) if (o.getCodigo().equalsIgnoreCase(codO) && o.getEstado() == OrdenServicio.EstadoOrden.PENDIENTE) { System.out.println(jPrep.agregarOrden(o) ? "Orden asignada a jornada." : "No se pudo asignar."); return; } System.out.println("Orden no encontrada."); }
    }

    private static void iniciarJornada(Scanner scanner) { System.out.print("Codigo de cuadrilla: "); String cod = scanner.nextLine(); for (Jornada j : listaJornadas) if (j.getCuadrilla().getCodigo().equalsIgnoreCase(cod) && j.getEstado() == Jornada.EstadoJornada.EN_PREPARACION) { j.iniciarJornada(); System.out.println("Jornada iniciada."); return; } System.out.println("No se encontro jornada en preparacion."); }

    private static void registrarVisita(Scanner scanner) {
        System.out.print("Codigo de orden en atencion: "); String cod = scanner.nextLine(); OrdenServicio ordenObj = null; for (OrdenServicio o : listaOrdenes) if (o.getCodigo().equalsIgnoreCase(cod) && o.getEstado() == OrdenServicio.EstadoOrden.EN_ATENCION) { ordenObj = o; break; }
        if (ordenObj == null) { System.out.println("Orden no encontrada o no esta en atencion."); return; }
        System.out.print("Resultado (1. Completada, 2. Fallida): "); int res = Integer.parseInt(scanner.nextLine());
        if (res == 1) { ordenObj.setEstado(OrdenServicio.EstadoOrden.COMPLETADA); System.out.println("Completada."); } else { ordenObj.incrementarVisitas(); if (ordenObj.getVisitasRealizadas() > 1) { ingresoTotalVisitasAdicionales += 40.0; System.out.println("Cargo adicional de Q40.00 por visita extra."); } if (ordenObj.getVisitasRealizadas() >= ordenObj.obtenerMaxVisitas()) { ordenObj.setEstado(OrdenServicio.EstadoOrden.CANCELADA); System.out.println("Limite de visitas alcanzado. Orden CANCELADA."); } else { ordenObj.setEstado(OrdenServicio.EstadoOrden.PENDIENTE); System.out.println("Regresa a PENDIENTE."); } }
    }

    private static void consultarGeneral() { System.out.println("\n--- LISTADO DE ORDENES ---"); for (OrdenServicio o : listaOrdenes) System.out.println("[" + o.getCodigo() + "] " + o.getNombreCategoria() + " | Cliente: " + o.getNombreCliente() + " | Estado: " + o.getEstado()); }
    private static void mostrarReportes() { System.out.println("\n--- REPORTES GERENCIALES ---"); System.out.println("Ingresos por registros: Q" + String.format("%.2f", ingresoTotalRegistro)); System.out.println("Ingresos por visitas adicionales: Q" + String.format("%.2f", ingresoTotalVisitasAdicionales)); System.out.println("Ingreso Total General: Q" + String.format("%.2f", ingresoTotalRegistro + ingresoTotalVisitasAdicionales)); System.out.println("\nJornadas no cerradas:"); for (Jornada j : listaJornadas) if (j.getEstado() != Jornada.EstadoJornada.CERRADA) { double horasAsig = j.getOrdenesAsignadas().stream().mapToDouble(OrdenServicio::getHorasEstimadas).sum(); System.out.println("- Cuadrilla: " + j.getCuadrilla().getCodigo() + " | Estado: " + j.getEstado() + " | Horas asignadas: " + horasAsig + "/" + j.getCuadrilla().getCapacidadHoras()); } }
}

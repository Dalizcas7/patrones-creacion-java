import java.util.Arrays;
import java.util.List;

/**
 * Demostración del patrón Abstract Factory para procesar informes.
 * Formato esperado: CAT_TIPO_identificador.txt
 */
public class Main {

    enum Categoria { ENTRADA, SALIDA, MIXTA }
    enum TipoInforme { FACTURA, COMPRA, PEDIDO }

    interface ProcesadorInforme {
        void procesar(String nombreArchivo);
    }

    // Fábrica abstracta: define la familia común de productos.
    interface AbstractFactory {
        ProcesadorInforme crearFactura();
        ProcesadorInforme crearCompra();
    }

    // Extensión específica: PEDIDO solo está disponible para ENTRADA.
    interface EntradaFactory extends AbstractFactory {
        ProcesadorInforme crearPedido();
    }

    // Productos concretos de ENTRADA.
    static class EntradaFactura implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de ENTRADA de tipo FACTURA: " + nombre);
        }
    }
    static class EntradaCompra implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de ENTRADA de tipo COMPRA: " + nombre);
        }
    }
    static class EntradaPedido implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de ENTRADA de tipo PEDIDO: " + nombre);
        }
    }

    // Productos concretos de SALIDA.
    static class SalidaFactura implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de SALIDA de tipo FACTURA: " + nombre);
        }
    }
    static class SalidaCompra implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de SALIDA de tipo COMPRA: " + nombre);
        }
    }

    // Productos concretos de MIXTA.
    static class MixtaFactura implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de MIXTA de tipo FACTURA: " + nombre);
        }
    }
    static class MixtaCompra implements ProcesadorInforme {
        public void procesar(String nombre) {
            System.out.println("Procesando informe de MIXTA de tipo COMPRA: " + nombre);
        }
    }

    // Fábricas concretas.
    static class FabricaEntrada implements EntradaFactory {
        public ProcesadorInforme crearFactura() { return new EntradaFactura(); }
        public ProcesadorInforme crearCompra()  { return new EntradaCompra(); }
        public ProcesadorInforme crearPedido()  { return new EntradaPedido(); }
    }
    static class FabricaSalida implements AbstractFactory {
        public ProcesadorInforme crearFactura() { return new SalidaFactura(); }
        public ProcesadorInforme crearCompra()  { return new SalidaCompra(); }
    }
    static class FabricaMixta implements AbstractFactory {
        public ProcesadorInforme crearFactura() { return new MixtaFactura(); }
        public ProcesadorInforme crearCompra()  { return new MixtaCompra(); }
    }

    static class ProcesadorInformes {
        private final AbstractFactory entrada = new FabricaEntrada();
        private final AbstractFactory salida = new FabricaSalida();
        private final AbstractFactory mixta = new FabricaMixta();

        void procesarInformes(String[] informes) {
            for (String nombre : informes) {
                try {
                    procesarUno(nombre);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Archivo no procesado: " + nombre
                            + " | Motivo: " + ex.getMessage());
                }
            }
        }

        private void procesarUno(String nombre) {
            if (nombre == null) {
                throw new IllegalArgumentException("el nombre es nulo");
            }
            String[] partes = nombre.split("_");
            if (partes.length != 3 || !partes[2].toLowerCase().endsWith(".txt")) {
                throw new IllegalArgumentException("formato esperado CAT_TIPO_id.txt");
            }

            Categoria categoria;
            TipoInforme tipo;
            try {
                String codigoCategoria = partes[0];
                if ("ENT".equals(codigoCategoria)) categoria = Categoria.ENTRADA;
                else if ("SAL".equals(codigoCategoria)) categoria = Categoria.SALIDA;
                else if ("MIX".equals(codigoCategoria)) categoria = Categoria.MIXTA;
                else throw new IllegalArgumentException("categoría desconocida: " + codigoCategoria);
                String codigoTipo = partes[1];
                if ("FAC".equals(codigoTipo)) tipo = TipoInforme.FACTURA;
                else if ("COM".equals(codigoTipo)) tipo = TipoInforme.COMPRA;
                else if ("PED".equals(codigoTipo)) tipo = TipoInforme.PEDIDO;
                else throw new IllegalArgumentException("tipo desconocido: " + codigoTipo);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("categoría o tipo no reconocido");
            }

            AbstractFactory fabrica;
            switch (categoria) {
                case ENTRADA: fabrica = entrada; break;
                case SALIDA: fabrica = salida; break;
                case MIXTA: fabrica = mixta; break;
                default: throw new IllegalArgumentException("categoría no soportada");
            }

            ProcesadorInforme procesador;
            switch (tipo) {
                case FACTURA: procesador = fabrica.crearFactura(); break;
                case COMPRA: procesador = fabrica.crearCompra(); break;
                case PEDIDO:
                    if (!(fabrica instanceof EntradaFactory)) {
                        throw new IllegalArgumentException("PEDIDO solo está permitido en ENTRADA");
                    }
                    procesador = ((EntradaFactory) fabrica).crearPedido();
                    break;
                default: throw new IllegalArgumentException("tipo no soportado");
            }
            procesador.procesar(nombre);
        }
    }

    public static void main(String[] args) {
        String[] reports = {
            "ENT_FAC_001.txt",
            "ENT_COM_001.txt",
            "SAL_FAC_001.txt",
            "SAL_COM_001.txt",
            "MIX_FAC_001.txt",
            "MIX_COM_001.txt",
            "ENT_PED_001.txt",
            "SAL_PED_002.txt",   // prueba de combinación no permitida
            "ABC_FAC_003.txt"    // prueba de categoría no reconocida
        };
        new ProcesadorInformes().procesarInformes(reports);
    }
}

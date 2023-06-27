import java.util.ArrayList;

public class EncontrarOpsInterfaceImpl implements EncontrarOperacionesInterface {

    @Override
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros, ArrayList<Operadores> operadores, int cantNum, int resultadoABuscar) {
        if(cantNum < operadores.size() + 1 || numeros.size() < operadores.size() + 1) {
            return null;
        }
        //TODO reducir el array Numeros acorde a cantNum
        return obtenerOperaciones(numeros, operadores, resultadoABuscar, "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private static ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros,
                                                        ArrayList<Operadores> operadores,
                                                        int resultadoEsperado,
                                                        String expresion,
                                                        ArrayList<String> resultadoFinal,
                                                        ArrayList<Operadores> operadoresSolucion,
                                                        ArrayList<Integer> operandosSolucion) {
        if (numeros.size() <= operadores.size()) {
            return null;
        }
        if (operadores.isEmpty()) { //Si ya no quedan operadores que visitar, no puedo seguir generando expresion - Caso Base 1
            ArrayList<Integer> operandosSolucionAux = new ArrayList<>(operandosSolucion);
            operandosSolucionAux.add(numeros.get(0));
            String nuevaExpresion = expresion + numeros.get(0);
            System.out.println("nueva expresoion: " + nuevaExpresion);
            numeros.remove(0); // Saco el numero ya revisado.
            if (evaluarExpresion(operadoresSolucion, operandosSolucionAux) == resultadoEsperado) {
                resultadoFinal.add(nuevaExpresion);
            }
        }
        for (int i = 0; i < numeros.size(); i++) {
            int numeroActual = numeros.get(i);
            if (operadores.isEmpty()) { //Checkeo si es el ultimo caso de operador
                String nuevaExpresion = expresion + numeroActual;
                System.out.println("nueva expresoion: " + nuevaExpresion);
                ArrayList<Integer> operandosSolucionAux = new ArrayList<>(operandosSolucion);
                operandosSolucionAux.add(numeros.get(0));
                if (evaluarExpresion(operadoresSolucion, operandosSolucionAux) == resultadoEsperado) {
                    resultadoFinal.add(nuevaExpresion);
                }
            } else { //Todavia quedan operadores
                for (int j = 0; j < operadores.size(); j++) {
                    String operator = getOperadorChar(operadores.get(j));

                    ArrayList<Operadores> restoOperadores = new ArrayList<>(operadores); // Hago una copia de operadores y le quito el valor que evaluo antes de entrar al llamado recursivo
                    restoOperadores.remove(j);

                    ArrayList<Integer> restoNumeros = new ArrayList<>(numeros);
                    restoNumeros.remove(i); //Saco 1

                    ArrayList<Integer> operandosSolucionAux = new ArrayList<>(operandosSolucion);
                    operandosSolucionAux.add(numeroActual);

                    ArrayList<Operadores> operadoresSolucionAux = new ArrayList<>(operadoresSolucion);
                    operadoresSolucionAux.add(operadores.get(j));

                    String nuevaExpresion = expresion + numeroActual + operator;
                    System.out.println("nueva expresoion: " + nuevaExpresion);

                    obtenerOperaciones(restoNumeros, restoOperadores, resultadoEsperado, nuevaExpresion, resultadoFinal, operadoresSolucionAux, operandosSolucionAux); // Crece en profundidad para buscar la proxima combinacion.
                }
            }
        }
        return resultadoFinal;
    }

    private static String getOperadorChar(Operadores operador) {
        String operator = "";
        switch (operador) {
            case SUMA: {
                operator = "+";
                break;
            }
            case MULTI: {
                operator = "*";
                break;
            }
            case DIV: {
                operator = "/";
                break;
            }
            case RESTA: {
                operator = "-";
                break;
            }
        }
        return operator;
    }

    private static int evaluarExpresion(ArrayList<Operadores> operadores, ArrayList<Integer> operandos) { //2*4+8/1-5 - *, +, -, - // Operandos: 2,3,
        if (operandos.size() == operadores.size() + 1) {


            ArrayList<INodo> operaciones = new ArrayList<>(); //2, 4, 8, 1, 5
            for (Integer operando : operandos) {
                operaciones.add(new NodoOperando(operando));
            }
            int i = 0;
            while (i < operadores.size()) { // NodoMulti, NodoDivi, 5
                switch (operadores.get(i)) {
                    case MULTI: {
                        INodo operandoA = operaciones.remove(i);
                        INodo operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, new NodoOperadorMultiplicacion(operandoA, operandoB));
                        break;
                    }
                    case DIV: {
                        INodo operandoA = operaciones.remove(i);
                        INodo operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, new NodoOperadorDivision(operandoA, operandoB));
                        break;
                    }
                    default: {
                        i++;
                        break;
                    }
                }
            }

            i = 0;
            while (i < operadores.size()) { // K = o(2*n) donde n son los operadores. Peor caso es all SUMA y RESTA porque entra las 2 veces con la misma cantidad de operadores. El 2 -> lo saco me queda o(n) entonces K = 1
                switch (operadores.get(i)) {
                    case SUMA: {
                        INodo operandoA = operaciones.remove(i);
                        INodo operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, new NodoOperadorSuma(operandoA, operandoB));
                        break;
                    }
                    case RESTA: {
                        INodo operandoA = operaciones.remove(i);
                        INodo operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, new NodoOperadorResta(operandoA, operandoB));
                        break;
                    }
                    default: {
                        i++;
                        break;
                    }
                }
            }

            return operaciones.get(0).obtenerNumero();
        }
        return -1;
    }

    private interface INodo {
        int obtenerNumero();
    }

    private static abstract class NodoOperacion implements INodo {
        protected INodo mOperandoA;
        protected INodo mOperandoB;

        protected NodoOperacion(INodo operandoA, INodo operandoB) {
            mOperandoA = operandoA;
            mOperandoB = operandoB;
        }

    }

    private static class NodoOperadorDivision extends NodoOperacion {

        protected NodoOperadorDivision(INodo operandoA, INodo operandoB) {
            super(operandoA, operandoB);
        }

        @Override
        public int obtenerNumero() {
            return mOperandoA.obtenerNumero() / mOperandoB.obtenerNumero();
        }

    }

    private static class NodoOperadorMultiplicacion extends NodoOperacion {

        protected NodoOperadorMultiplicacion(INodo operandoA, INodo operandoB) {
            super(operandoA, operandoB);
        }

        @Override
        public int obtenerNumero() {
            return mOperandoA.obtenerNumero() * mOperandoB.obtenerNumero();
        }

    }

    private static class NodoOperadorSuma extends NodoOperacion {

        protected NodoOperadorSuma(INodo operandoA, INodo operandoB) {
            super(operandoA, operandoB);
        }

        @Override
        public int obtenerNumero() {
            return mOperandoA.obtenerNumero() + mOperandoB.obtenerNumero();
        }

    }

    private static class NodoOperadorResta extends NodoOperacion {

        protected NodoOperadorResta(INodo operandoA, INodo operandoB) {
            super(operandoA, operandoB);
        }

        @Override
        public int obtenerNumero() {
            return mOperandoA.obtenerNumero() - mOperandoB.obtenerNumero();
        }

    }

    private static class NodoOperando implements INodo {

        int mValor;

        public NodoOperando(int valor) {
            mValor = valor;
        }

        @Override
        public int obtenerNumero() {
            return mValor;
        }
    }
}

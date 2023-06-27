import java.util.ArrayList;

public class EncontrarOpsInterfaceImpl implements EncontrarOperacionesInterface {

    @Override
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros, ArrayList<Operadores> operadores, int cantNum, int resultadoABuscar) {
        if (cantNum < operadores.size() + 1 || numeros.size() < operadores.size() + 1) {
            return null;
        }
        //Consideramos que cantNum reducira el tamaño del array numeros original, en caso de que este sea mayor.
        if (numeros.size() > cantNum) {
            ArrayList<Integer> numerosFiltrados = new ArrayList<Integer>();
            for (int i = 0; i < cantNum; i++) {
                numerosFiltrados.add(numeros.get(i));
            }
            return obtenerOperaciones(numerosFiltrados, operadores, resultadoABuscar, "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
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
            if (evaluarExpresions(operadoresSolucion, operandosSolucionAux) == resultadoEsperado) {
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
                if (evaluarExpresions(operadoresSolucion, operandosSolucionAux) == resultadoEsperado) {
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

    private static int evaluarExpresions(ArrayList<Operadores> operadores, ArrayList<Integer> operandos) {
        if (operandos.size() == operadores.size() + 1) {
            ArrayList<Integer> operaciones = new ArrayList<>(operandos); // Copy operandos to operaciones
            int i = 0;
            while (i < operadores.size()) {
                switch (operadores.get(i)) {
                    case MULTI: {
                        int operandoA = operaciones.remove(i);
                        int operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, operandoA * operandoB);
                        break;
                    }
                    case DIV: {
                        int operandoA = operaciones.remove(i);
                        int operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, operandoA / operandoB);
                        break;
                    }
                    default: {
                        i++;
                        break;
                    }
                }
            }

            i = 0;
            while (i < operadores.size()) {
                switch (operadores.get(i)) {
                    case SUMA: {
                        int operandoA = operaciones.remove(i);
                        int operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, operandoA + operandoB);
                        break;
                    }
                    case RESTA: {
                        int operandoA = operaciones.remove(i);
                        int operandoB = operaciones.remove(i);
                        operadores.remove(i);
                        operaciones.add(i, operandoA - operandoB);
                        break;
                    }
                    default: {
                        i++;
                        break;
                    }
                }
            }

            return operaciones.get(0);
        }
        return -1;
    }
}

import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EncontrarOpsInterfaceImpl implements EncontrarOperacionesInterface {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");


    @Override
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros, ArrayList<Operadores> operadores, int cantNum, int resultadoABuscar) {
        return obtenerOperaciones(numeros, operadores, resultadoABuscar, "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private static ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros, ArrayList<Operadores> operadores, int resultadoEsperado, String expresion, ArrayList<String> resultadoFinal, ArrayList<Operadores> operadoresSolucion, ArrayList<Integer> operandosSolucion) {
        if(numeros.size() <= operadores.size()) {
            return null;
        }
        if (operadores.isEmpty()) { //Si ya no quedan operadores que visitar, no puedo seguir generando expresion - Caso Base 1
            ArrayList<Integer> operandosSolucionAux = new ArrayList<>(operandosSolucion);
            operandosSolucionAux.add(numeros.get(0));
            String nuevaExpresion = expresion + numeros.get(0);
            numeros.remove(0); // Saco el numero ya revisado.
            if (evaluarExpresion(operadoresSolucion, operandosSolucionAux) == resultadoEsperado) {
                System.out.println("Expresion valida agregada: " + nuevaExpresion);
                resultadoFinal.add(nuevaExpresion);
            } else {
                System.out.println("Expresion no valida:" + nuevaExpresion);
            }
        }
        for (int i = 0; i < numeros.size(); i++) {
            int numeroActual = numeros.get(i);
            if (operadores.isEmpty()) { //Checkeo si es el ultimo caso de operador
                String nuevaExpresion = expresion + numeroActual;
                ArrayList<Integer> operandosSolucionAux = new ArrayList<>(operandosSolucion);
                operandosSolucionAux.add(numeros.get(0));
                if (evaluarExpresion(operadoresSolucion, operandosSolucionAux) == resultadoEsperado) {
                    System.out.println("Expresion valida agregada: " + nuevaExpresion);
                    resultadoFinal.add(nuevaExpresion);
                } else {
                    System.out.println("Expresion no valida:" + nuevaExpresion);
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

                    //si es el ultimo operador que voy a agregar, antes de checkearlo, podria verificar si ya llegue al resultado. Si estoy en 10, ya sea que hago DIV/MULTI/RESTA/etc (evaluar cada caso) deberia cortar!.
                    if (restoOperadores.size() == 1) { //TODO REVISAR BIEN ESTO, PERO CREO QUE ESTA BIEN! - PODA
                        if (evaluarExpresion(operadoresSolucion, operandosSolucionAux) == resultadoEsperado && !restoOperadores.isEmpty() && !restoNumeros.isEmpty()) { //Poda, si ya llegue parcialmente al resultadoEsperado
                            Operadores operadorSiguiente = restoOperadores.get(0);
                            if (restoNumeros.size() == 1) { //Ultimo numero disponible a revisar
                                Integer numeroSiguiente = restoNumeros.get(0);
                                if (validarProximaOperacion(operadorSiguiente, numeroSiguiente)) {
                                    System.out.println("PODO no pruebo las siguientes");
                                    return resultadoFinal;
                                }
                            } else {
                                Integer numeroSiguiente = restoNumeros.get(0);
                                if (validarProximaOperacion(operadorSiguiente, numeroSiguiente)) { //Quedan otros numeros por revisar
                                    System.out.println("PODO parcial, no pruebo el numero siguiente.");
                                    restoNumeros.remove(0);
                                }
                            }
                        }
                    }

                    ArrayList<Operadores> operadoresSolucionAux = new ArrayList<>(operadoresSolucion);
                    operadoresSolucionAux.add(operadores.get(j));

                    String nuevaExpresion = expresion + numeroActual + operator;

                    System.out.println("Llamada recursiva nueva Expresion es:"+nuevaExpresion);
                    obtenerOperaciones(restoNumeros, restoOperadores, resultadoEsperado, nuevaExpresion, resultadoFinal, operadoresSolucionAux, operandosSolucionAux); // Crece en profundidad para buscar la proxima combinacion.
                    System.out.println("Salgo llamada recursiva Expresion es:"+expresion);

                }
            }
        }
        return resultadoFinal;
    }

    private static boolean validarProximaOperacion(Operadores operadorSiguiente, Integer numeroSiguiente) {
        return operadorSiguiente == Operadores.SUMA && numeroSiguiente > 0
                || operadorSiguiente == Operadores.MULTI && numeroSiguiente > 1
                || operadorSiguiente == Operadores.DIV && numeroSiguiente != 1
                || operadorSiguiente == Operadores.RESTA && numeroSiguiente > 0;
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

    private static int evaluateExpression(String expression) { //Adaptar esto, para que la expresion se evalue via 2 listas y un switch. o(n)
        javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
        javax.script.ScriptEngine engine = manager.getEngineByName("js");
        try {
            return (int) engine.eval(expression);
        } catch (javax.script.ScriptException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //operadores = SUMA, RESTA
    //Operandos = 3, 5, 6
    private static int evaluarExpresion(ArrayList<Operadores> operadores, ArrayList<Integer> operandos) {
        if (operandos.size() == operadores.size() + 1) {
            ArrayList<Operadores> subCalculoOperadores = new ArrayList<>();
            ArrayList<Integer> subCalculoOperandos = new ArrayList<>();

            ArrayList<Operadores> auxiliarOperadores = new ArrayList<>(operadores);
            ArrayList<Integer> auxiliarOperandos = new ArrayList<>(operandos);
            for (int i = 0; i < operadores.size(); i++) {
                Operadores operadorActual = operadores.get(i);
                if (operadorActual == Operadores.MULTI || operadorActual == Operadores.DIV) {
                    subCalculoOperadores.add(operadorActual);
                    auxiliarOperadores.remove(i);

                    subCalculoOperandos.add(operandos.get(i));
                    auxiliarOperandos.remove(i);
                    subCalculoOperandos.add(operandos.get(i+1));
                    auxiliarOperandos.remove(i);
                    auxiliarOperandos.add(i, realizarCalculo(subCalculoOperadores, subCalculoOperandos));
                }
            }

            return realizarCalculo(auxiliarOperadores, auxiliarOperandos);
        } else {
            return -1;
        }
    }

    private static int realizarCalculo(ArrayList<Operadores> operadores, ArrayList<Integer> operandos) {
        int result = operandos.get(0);
        operandos.remove(0);
        //Si tiene MULTI o DIV   [4 + 2 * 3 - 2 / 5] [4,2,3,2,5] [SUMA,MULTI,RESTA,DIV]
        for (int i = 0; i < operadores.size(); i++) {
            switch (operadores.get(i)) {
                case SUMA: {
                    result = result + operandos.get(i);
                    break;
                }
                case MULTI: {
                    result = result * operandos.get(i);
                    break;
                }
                case DIV: {
                    result = result / operandos.get(i);
                    break;
                }
                case RESTA: {
                    result = result - operandos.get(i);
                    break;
                }
            }
        }
        return result;
    }
}

import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EncontrarOpsInterfaceImpl implements EncontrarOperacionesInterface {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");


    @Override
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros, ArrayList<Operadores> operadores, int cantNum, int resultadoABuscar) {
        return obtenerOperaciones(numeros, operadores, resultadoABuscar, "", new ArrayList<>());
    }

    private static ArrayList<String> obtenerOperaciones(ArrayList<Integer> numeros, ArrayList<Operadores> operadores, int resultadoEsperado, String expresion, ArrayList<String> resultadoFinal) {
        if (operadores.isEmpty()) { //Si ya no quedan operadores que visitar, no puedo seguir generando expresion - Caso Base 1
            String nuevaExpresion = expresion + numeros.get(0);
            numeros.remove(0); // Saco el numero ya revisado.
            if (evaluateExpression(nuevaExpresion) == resultadoEsperado) {
                System.out.println("Expresion valida agregada: " + nuevaExpresion);
                resultadoFinal.add(nuevaExpresion);
            } else {
                System.out.println("Expresion no valida:" + nuevaExpresion);
            }
        }
        for (int i = 0; i < numeros.size(); i++) {
            int number = numeros.get(i);
            if (operadores.isEmpty()) { //Checkeo si es el ultimo caso de operador
                System.out.println("sin operadores");
                String nuevaExpresion = expresion + number;
                if (evaluateExpression(nuevaExpresion) == resultadoEsperado) {
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

                    System.out.println("Evaluo expresion parcial antes de PODA:" + expresion + number);
                    if (evaluateExpression(expresion + number) == resultadoEsperado && !restoOperadores.isEmpty() && !restoNumeros.isEmpty()) { //Poda, si ya llegue parcialmente al resultadoEsperado
                        if (restoOperadores.get(0) == Operadores.SUMA && restoNumeros.get(0) > 0
                                || restoOperadores.get(0) == Operadores.MULTI && restoNumeros.get(0) > 1) {
                            System.out.println("PODO no pruebo las siguientes");
                            return resultadoFinal;
                        }
                    }

                    String nuevaExpresion = expresion + number + operator;

                    obtenerOperaciones(restoNumeros, restoOperadores, resultadoEsperado, nuevaExpresion, resultadoFinal); // Crece en profundidad para buscar la proxima combinacion.
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

    private static int evaluateExpression2(String expression) {
        javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
        javax.script.ScriptEngine engine = manager.getEngineByName("js");
        try {
            return (int) engine.eval(expression);
        } catch (javax.script.ScriptException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

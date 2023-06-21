import java.util.ArrayList;
import java.util.List;

public class EncontrarOperacionesInterfaceImpl implements EncontrarOperacionesInterface {

    @Override
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> possibleNumbers,
                                                ArrayList<Operadores> possibleOperators,
                                                int availableNumbersQty,
                                                int expectedResult) {
        return new ArrayList<>();

//        return obtenerOps(possibleNumbers, possibleOperators, availableNumbersQty, expectedResult, "", possibleSolutions);
    }


    private ArrayList<String> obtenerOps(ArrayList<Integer> possibleNumbers,
                                         ArrayList<Operadores> possibleOperators,
                                         int availableNumbersQty,
                                         int expectedResult,
                                         ArrayList<Operadores> operadoresSolucion,
                                         ArrayList<String> possibleSolutions) {


        if (possibleOperators.isEmpty()) {
//            if (evaluar(combinacion) == expectedResult) {
//                possibleSolutions.add(combinacion);
//            }
            return possibleSolutions; // Este no se si va aca.
        } else {
            for (Integer number : possibleNumbers) {
                ArrayList<Integer> remainingNumbers = new ArrayList<>(possibleNumbers);
                remainingNumbers.remove(number);
                for (Operadores operator : possibleOperators) {
                    ArrayList<Operadores> remainingOperators = new ArrayList<>(possibleOperators);
                    remainingOperators.remove(operator); //b = 1 (porque saco de a un oiperador a la vez, para el caso base operadores = 0
                    obtenerOperaciones(remainingNumbers, remainingOperators, availableNumbersQty, expectedResult);
                }
            }
        }
        return possibleSolutions; // Este no se si va aca.
    }

//    private Integer evaluar(String combinacion) { //"4,SUMA,2,RESTA,1"
//        resultado
//        List operadores
//        List numeros
//        for (numero:
//             numeros) {
//            switch(operadores) {
//                Operadores.SUMA -> resultad = numero + numeros+1
//                        Operadores.RESTA -> resultad = numero - numeros+1
//            }
//        }
//
//        return 0; //TODO sacar esta falopa
//    }

//    private static List<String> backtrack(List<Integer> numbers, List<String> operators, int target, int numCount, String expression, List<String> results) {
//
//        if (evaluateExpression(expression) == target) { // TODO evaluateExpression
//            results.add(expression);
//        }
//
//        if (numCount == 0) { //b = 1 (numCount – 1)
//            return results;
//        } else {
//
//            for (int i = 0; i < numbers.size(); i++) {
//
//                Integer number = numbers.get(i);
//
//                List<Integer> remainingNumbers = new ArrayList<>(numbers);
//
//                remainingNumbers.remove(i);
//
//
//                for (int j = 0; j < operators.size(); j++) {
//
//                    String operator = operators.get(j);
//
//                    List<String> remainingOperators = new ArrayList<>(operators);
//
//                    remainingOperators.remove(j);
//
//
//                    backtrack(remainingNumbers, remainingOperators, target, numCount – 1, expression + number + operator, results);
//
//                }
//
//            }
//
//        }
//    }


    /**
     * Puedo meter el metodo que yo quiera adentro de esto
     */
}

/**
 * BuscarCombinaciones(Array números, Array operadores, int objetivo, int cantNum, String combinación, Array resultado):
 * si(cantNum==0):
 * si(evaluar(combinación)==objetivo):
 * resultado.agregar(combinación)
 * sino:
 * para cada numero in números:
 * Array numerosRestantes=números
 * numerosRestantes.eliminar(numero)
 * para cada operador en operadores:
 * Array operadoresRestantes=operadores;
 * operadoresRestantes.eliminar(operador)
 * BuscarCombinaciones(numerosRestantes, operadoresRestantes, objetivo, cantNum-1, String(combinación+numero+operador), resultado)
 */
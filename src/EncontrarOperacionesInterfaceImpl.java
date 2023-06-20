import java.util.ArrayList;
import java.util.List;

public class EncontrarOperacionesInterfaceImpl implements EncontrarOperacionesInterface {

    @Override
    public ArrayList<String> obtenerOperaciones(ArrayList<Integer> possibleNumbers,
                                                ArrayList<Operadores> possibleOperators,
                                                int availableNumbersQty,
                                                int expectedResult) {
        ArrayList<String> possibleSolutions = new ArrayList<>();
        if (availableNumbersQty == 0) {
            if (evaluar(combinacion) == expectedResult) {
                possibleSolutions.add(combinacion);
            }
        } else {
            for (Integer number : possibleNumbers) {
                ArrayList<Integer> remainingNumbers = new ArrayList<>(possibleNumbers);
                remainingNumbers.remove(number);
                for (Operadores operator : possibleOperators) {
                    ArrayList<Operadores> remainingOperators = new ArrayList<>(possibleOperators);
                    remainingOperators.remove(operator);
                    obtenerOperaciones(remainingNumbers, remainingOperators, availableNumbersQty-1, expectedResult); //235, SUMA, 2, 1

                }
            }
        }
        return possibleSolutions;
    }

    private ArrayList<String> obtenerOperaciones(ArrayList<Integer> possibleNumbers,
                                                ArrayList<Operadores> possibleOperators,
                                                int availableNumbersQty,
                                                int expectedResult,
                                                 ) {
        ArrayList<String> possibleSolutions = new ArrayList<>();
        if (availableNumbersQty == 0) {
//            if (evaluar(combinacion) == expectedResult) {
//                possibleSolutions.add(combinacion);
//            }
        } else {
            for (Integer number : possibleNumbers) {
                ArrayList<Integer> remainingNumbers = new ArrayList<>(possibleNumbers);
                remainingNumbers.remove(number);
                for (Operadores operator : possibleOperators) {
                    ArrayList<Operadores> remainingOperators = new ArrayList<>(possibleOperators);
                    remainingOperators.remove(operator);
                    obtenerOperaciones(remainingNumbers, remainingOperators, availableNumbersQty-1, expectedResult); //235, SUMA, 2, 1

                }
            }
        }
        return possibleSolutions;
    }

    private static void backtrack(List<Integer> numbers, List<String> operators, int target, int numCount, String expression, List<String> results) {

        if(numCount == 0) {

            if(evaluateExpression(expression) == target) { // TODO evaluateExpression

                results.add(expression);

            }

        } else{

            for( int i = 0; i < numbers.size(); i++) {

                Integer number = numbers.get(i);

                List<Integer> remainingNumbers = new ArrayList<>(numbers);

                remainingNumbers.remove(i);


                for (int j = 0; j < operators.size(); j++) {

                    String operator = operators.get(j);

                    List<String> remainingOperators = new ArrayList<>(operators);

                    remainingOperators.remove(j);


                    backtrack(remainingNumbers, remainingOperators, target, numCount – 1, expression + number + operator, results);

                }

            }

        }
    }


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
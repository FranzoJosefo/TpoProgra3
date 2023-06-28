import java.util.ArrayList;

public class PrincipalEncontrarOperaciones {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EncontrarOpsInterfaceImpl operaciones = new EncontrarOpsInterfaceImpl();
        ArrayList<Integer> numeros = new ArrayList<Integer>(); //2,3,4,5,10,9,1,8
        numeros.add(2);
        numeros.add(3);
        numeros.add(4);
        numeros.add(5);
        numeros.add(6);
        numeros.add(8);


        ArrayList<Operadores> operadores = new ArrayList<Operadores>();
        operadores.add(Operadores.SUMA);
        operadores.add(Operadores.SUMA);
        operadores.add(Operadores.SUMA);
        operadores.add(Operadores.SUMA);
        operadores.add(Operadores.SUMA);

//        operadores.add(Operadores.SUMA);
//        operadores.add(Operadores.MULTI);
////        operadores.add(Operadores.RESTA);
//        operadores.add(Operadores.SUMA);
//        operadores.add(Operadores.RESTA);
//
//        operadores.add(Operadores.DIV);
//        operadores.add(Operadores.MULTI);
//        operadores.add(Operadores.SUMA);


        int cantNum = 8;
        int resultadoABuscar = 1;
        long start = System.currentTimeMillis();
        ArrayList<String> resultado = operaciones.obtenerOperaciones(numeros, operadores, cantNum, resultadoABuscar);


        if (resultado != null) {
            int i = 0;
            System.out.println("Las operaciones resultantes que cumplan con "+resultadoABuscar+" son:");
            while (i < resultado.size()) {
                System.out.println(resultado.get(i));
                i++;
            }
        }
        else {
            System.out.println("El resultado arrojado es nulo");
        }

        long end = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion en mili-segundos: "+ (end-start));
    }
}
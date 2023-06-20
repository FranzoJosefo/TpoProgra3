import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        tuVieja();
    }

    public static void tuVieja() {
        ArrayList<String> arrayCaca = new ArrayList<>();
        arrayCaca.add("EnTanga");
        arrayCaca.add("Lalala");
        ArrayList<String> arrayCopiadop = copyArray(arrayCaca);
        arrayCopiadop.remove(0);
        System.out.println(arrayCaca.get(0));
        System.out.println(arrayCaca.get(1));
        System.out.println("/n");
        System.out.println(arrayCopiadop.get(0));
    }

    public static <T> ArrayList<T> copyArray(ArrayList<T> originalArray) {
        return new ArrayList<>(originalArray);
    }


}
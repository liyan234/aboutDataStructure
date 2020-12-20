package lesson3;

public class MethodArgument {



    public static void main(String[] args) {
        int i = 0;
        help(i);
        System.out.println(i);
    }

    private static void help(int i) {
        i = 1;
    }

}

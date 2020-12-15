package org.example.test;

public class Test {

    private static class P{
        protected int x = 3;

        public P(){
            System.out.println(x);
            s();
            System.out.println(x);
        }
        protected void s(){
            x = 4;
        }
    }

    private static class C extends P{
        protected int x = 1;

        public C(){
            // super();
            System.out.println(x);
        }
        protected void s(){
            x = 6;
        }
    }

    public static void main(String[] args) {
        P c = new C();
        System.out.println(c.x);
    }
}



package org.example.test;

public class Design1 {

    private interface Run{
        void doSomething();
    }

    private static class Person implements Run{

        @Override
        public void doSomething() {
            System.out.println("Person do ");
        }
    }


    private static class C {
        private Run run;

        public C(Run run) {
            this.run = run;
        }

        public void doC() {
            System.out.println("do x");
            run.doSomething();
        }
    }


    public static void main(String[] args) {
        Run run = new Person();
        C c = new C(run);
        c.doC();
    }
}

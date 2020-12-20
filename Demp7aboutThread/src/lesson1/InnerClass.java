package lesson1;

public class InnerClass {

    // 静态内部类 直接访问就可以
    // 用 InnerClass.A 就可以调用 和普通类的使用没有区别
    // 在使用的时候需要明确的说明是那个类的内部类
    public static class A{
        public void printX() {
            System.out.println('x');
        }
    }

    public static void main(String[] args) {
        // 匿名内部类
        // 只是从新定义了一个A的子类
        // 重写了方法
        // 需要明确的调用才会执行方法
        A a = new A(){
            @Override
            public void printX() {
                System.out.println("y");
            }
        };

    }

}

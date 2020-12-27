import frank.Duck;
import frank.Duck2;
import frank.Duck3;
import frank.DuckShop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        /**
         * Spring开启容器的方式 ApplicationContext
         * 应用上下文（可以配置并且管理上下文，及其他工作）
         *
         * ClassPathXmlApplicationContext根据classpath路径，指定一个xml文件(配置文件)
         * 并且根据配置文件来完成配置工作，比如bean的实列化
         * 创建一个
         */
        ApplicationContext context = new
                ClassPathXmlApplicationContext("applications.xml");


        //通过bean的名称获取bean对象，bean名称就是xml中bean的id
        String a = (String) context.getBean("li");
        //String b = (String) context.getBean("java.lang.String#0");
        System.out.println(a);
        //System.out.println(b);

        //通过类型获取bean对象，如果类型有多个对象，就会报错，
        // 只支持一个类型的对象
        //String c = context.getBean(String.class);
        //System.out.println(c);

        Duck d1 = (Duck) context.getBean("d1");
        System.out.println(d1);

        Duck2 d2 = (Duck2) context.getBean("d2");
        System.out.println(d2);

        Duck3 d3 = (Duck3) context.getBean("dk1");
        System.out.println(d3);

        DuckShop d4 = (DuckShop) context.getBean("shop");
        System.out.println(d4);
    }
}

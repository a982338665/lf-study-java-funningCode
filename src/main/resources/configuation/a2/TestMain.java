package configuation.a2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestMain {
    public static void main(String[] args) {

        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

        // 如果加载spring-context.xml文件：
        // ApplicationContext context = new
        // ClassPathXmlApplicationContext("spring-context.xml");
        
         //获取bean--->测试注册进spring的bean是单例模式还是非单例模式，默认单例，可指定
        TestBean tb = (TestBean) context.getBean("testBean");
        tb.sayHello();
        System.out.println("-------->"+tb);
        TestBean tb2 = (TestBean) context.getBean("testBean");
        tb2.sayHello();
        System.out.println("-------->"+tb2);
    }
}
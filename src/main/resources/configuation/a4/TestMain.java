package configuation.a4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import configuation.a1.TestConfiguration;
import configuation.a2.TestBean;

public class TestMain {
    public static void main(String[] args) {

        // @Configuration注解的spring容器加载方式，用AnnotationConfigApplicationContext替换ClassPathXmlApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        TestBean tb = (TestBean) context.getBean("testBean");
        tb.sayHello();

//        ApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.register(AppContext.class)
    }
}
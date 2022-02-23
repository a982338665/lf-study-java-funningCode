package configuation.a2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象
 *
 * 注：
    (1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同；
    (2)、@Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域；
    (3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，
        当然需要配置@ComponentScan注解进行自动扫描。
    ————————————————————
    @Bean生命周期管理：
    可以使用基于 Java 的配置来管理 bean 的生命周期。@Bean 支持两种属性，即 initMethod 和destroyMethod，
    这些属性可用于定义生命周期方法。在实例化 bean 或即将销毁它时，容器便可调用生命周期方法。生命周期方法也称为回调方法，
    因为它将由容器调用。使用 @Bean 注释注册的 bean 也支持 JSR-250 规定的标准 @PostConstruct 和 @PreDestroy 注释。
    如果您正在使用 XML 方法来定义 bean，那么就应该使用 bean 元素来定义生命周期回调方法。
    以下代码显示了在 XML 配置中通常使用 bean 元素定义回调的方法。
 */
@Configuration
public class TestConfiguration {
    public TestConfiguration() {
        System.out.println("TestConfiguration容器启动初始化。。。");
    }

    // @Bean注解注册bean,同时可以指定初始化和销毁方法
    // @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    @Bean
    @Scope("prototype")
    public TestBean testBean() {
        return new TestBean();
    }
}
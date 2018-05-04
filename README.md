# springboot-dubbo-zookeeper

采用了两种不同的方法赖整合dubbo，第一种直接通过xml配置直接整合dubbo，第二种方式则是依赖spring-boot-starter-dubbo，免去了xml配置。

其中接口部分也分离了出来，通过maven引入，免去了服务提供者和消费者的重复编码。

直接整合dubbo方式：

1.引入dubbo依赖，服务提供者和消费者皆须引入
```xml
    <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.5.4</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring</artifactId>
                </exclusion>
            </exclusions>        
    </dependency>
```
2.定义公共接口
```java
public interface DemoService {

    String sayHello(String string);
}
```
3.pom文件中引入接口项目，provider和consumer都需要
```xml
        <dependency>
            <groupId>com.lgd</groupId>
            <artifactId>dubbo-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```
4.服务提供者provider创建dubbo-provider.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://code.alibabatech.com/schema/dubbo
       http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
    <!-- 配置可参考 http://dubbo.io/User+Guide-zh.htm -->
    <!-- 服务提供方应用名，用于计算依赖关系 -->
    <dubbo:application name="dubbo-provider" owner="dubbo-provider"/>
    <!-- 定义 zookeeper 注册中心地址及协议 -->
    <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181" client="zkclient"/>
    <!-- 定义 Dubbo 协议名称及使用的端口，dubbo 协议缺省端口为 20880，如果配置为 -1 或者没有配置 port，则会分配一个没有被占用的端口 -->
    <dubbo:protocol name="dubbo" port="-1"/>

    <!-- 使用xml配置方式暴露接口 -->
    <!--<dubbo:service interface="com.lgd.dubboapi.service.DemoService" ref="demoService" timeout="10000"/>
    <bean id="demoService" class="com.lgd.dubboprovide.service.DemoServiceImpl" />-->

    <!-- 使用注解方式暴露接口 -->
    <dubbo:annotation package="com.lgd.dubboprovide" />
</beans>
```
5.在启动类加载此文件
```java
@SpringBootApplication
@ImportResource(value = {"classpath:dubbo-provider.xml"})
public class DubboProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvideApplication.class, args);
    }
}
```
6.服务提供者实现接口
```java
@Service
//此service注解为com.alibaba.dubbo.config.annotation.Service，并非spring注解
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String string) {
        String str = "provider :" + string;
        System.out.println(str);
        return str;
    }
}
```
7.服务提供者的配置已完成，进行消费者consumer配置，创建dubbo-consumers.xml，并一样地在启动类进行加载
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://code.alibabatech.com/schema/dubbo
       http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
    <!-- 配置可参考 http://dubbo.io/User+Guide-zh.htm -->
    <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
    <dubbo:application  name="dubbo-consumer" owner="dubbo-consumer"/>
    <!-- 定义 zookeeper 注册中心地址及协议 -->
    <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181" client="zkclient" />

    <!-- 使用xml配置方式暴露接口 -->
    <!--<dubbo:reference id="demoService" interface="com.lgd.dubboapi.service.DemoService"/>-->

    <!-- 使用注解方式暴露接口 -->
    <dubbo:annotation package="com.lgd.dubboconsumer" />
</beans>
```
8.消费者调用接口
```java
@Controller
public class DemoController {
    /*@Autowired 用于xml配置注入*/
    @Reference //注解方式
    private DemoService demoService;

    @RequestMapping(value="sayHello")
    @ResponseBody
    public String sayHello(HttpServletRequest request, HttpServletResponse response){
        String string = "consumer : hello";
        System.out.println(string);
        string = string + "<br>" + demoService.sayHello("hello");
        return string;
    }
}
```
9.此外还需配置服务提供者和消费者的tomcat端口，防止冲突，在application.properties进行一下操作
```xml
#指定tomcat端口
server.port=8012
```
10.至此dubbo已经整合完毕，首先启动zookeeper注册中心，然后启动服务提供者，再启动消费者，通过消费者路径访问/sayHello可检验是否成功。

依赖spring-boot-starter-dubbo方式整合dubbo就简单多了，免去了一切xml配置的烦恼。

1.同样的引入接口项目必不可少
```xml
        <dependency>
            <groupId>com.lgd</groupId>
            <artifactId>dubbo-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```
2.引入spring-boot-starter-dubbo依赖
```xml
        <!-- springboot dubbo依赖 -->
        <dependency>
            <groupId>io.dubbo.springboot</groupId>
            <artifactId>spring-boot-starter-dubbo</artifactId>
            <version>1.0.0</version>
        </dependency>
```
3.服务提供者的application.properties配置
```xml
spring.dubbo.application.name=provider
spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
spring.dubbo.protocol.name=dubbo
spring.dubbo.protocol.port=20880
spring.dubbo.scan=com.lingd.springbootdubboprovider

#指定tomcat端口
server.port=8011
```
4.消费者的applocation.properties配置
```xml
spring.dubbo.application.name=consumer
#注册地址
spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
spring.dubbo.protocol.name=dubbo
spring.dubbo.protocol.port=20880
#扫描路径
spring.dubbo.scan=com.lingd.springbootdubboconsumer

#指定tomcat端口
server.port=8012
```
5.接下来的服务提供者接口的实现以及消费者接口的调用皆和前面的整合一样，依赖spring-boot-starter-dubbo的整合只需配置好application.properties即可，省去了麻烦的xml配置，非常的简便。

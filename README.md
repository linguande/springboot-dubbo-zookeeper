# springboot-dubbo-zookeeper

采用了两种不同的方法赖整合dubbo，第一种直接通过xml配置直接整合dubbo，第二种方式则是依赖spring-boot-starter-dubbo，免去了xml配置。

其中接口部分也分离了出来，通过maven引入，免去了服务提供者和消费者的重复编码。

1.引入dubbo依赖
    `<dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.5.4</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring</artifactId>
                </exclusion>
            </exclusions>
    </dependency>`

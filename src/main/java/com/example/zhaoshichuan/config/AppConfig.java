package com.example.zhaoshichuan.config;


import com.example.zhaoshichuan.bean.Sheep;
import com.example.zhaoshichuan.bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * springboot摒弃xml配置，改为全注解驱动
 */

@EnableConfigurationProperties(Sheep.class) // 放入容器中，并与配置文件进行属性绑定，和@ConfigurationProperties配合使用
//@Import(FastsqlException.class) //向容器中放入指定类的组件，比如第三方的jar
@SpringBootConfiguration// 说明是一个springboot配置类，和@Configuration没什么区别
@Configuration // 说明这是一个配置类，代替之前的xml文件，配置类本身也是容器中的一个组件
public class AppConfig {
    //组件默认是单实例的
    @Scope("prototype") // 设定范围，容器中的多实例之间不相等
    @Bean // 代替之前的Bean标签，组件在容器中的名字是类名
    public User user(){
        User usr = new User();
        usr.setId(1L);
        usr.setName("hh");
        return usr;
    }

}

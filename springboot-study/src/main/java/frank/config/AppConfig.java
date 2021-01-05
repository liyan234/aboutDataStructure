package frank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import frank.config.interceptor.LoginInterceptor;
import frank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注意：静态资源也会被拦截
        /**
         * 添加web的配置：添加拦截器（根据路径惊醒拦截）
         *  /* 代表以及路径 ， 如/user/* 可以匹配到 /user/adc 不能匹配/user/adb/1
         *  /** 代表多级路径
         * */
        //实现用户会话管理的功能
        registry.addInterceptor(new LoginInterceptor(objectMapper))//链式方法设计中：当前类型的方法，返回值就是this
                .excludePathPatterns("/user/**") //添加拦截的路劲
                .excludePathPatterns("/user/login") ;//但是不会拦截这个路径
    }

    @Bean
    public Map<Integer, Integer> test1() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,100);
        map.put(2,200);
        return map;
    }

    @Bean
    public Map<Integer, Integer> test2() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(3,300);
        map.put(4,400);
        return map;
    }

    @Bean
    public User user1() {
        User u = new User();
        u.setName("xxx");
        u.setPassword("111");
        return u;
    }

    @Bean
    public User user2() {
        User u = new User();
        u.setName("yyy");
        u.setPassword("222");
        return u;
    }
}

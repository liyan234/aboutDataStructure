package frank.controller;

import frank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ObjIntConsumer;

//当前类型注册实例到容器中，并且指定为web请求的处理
@Controller
//@RequestMapping可以定义请求相关的配置，比如路径，请求方法等等
//使用在类和方法上
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Map<Integer, Integer> test1;
    @Resource
    private Map<Integer, Integer> test2;
//
//    @Autowired
//    @Qualifier("user1")//变量名和bean的名称不一致的时候，搭配@Qualicfier使用确定变量
//    //@Qualifier中的名称和须和@bean的名称一致
//    private User user;//变量名和bean的名称一致。

    @Resource(name="user1")
    //当有多个实例对象的的时候，变量名和@bean中的不一致的时候，需要在后面跟上@bean的变量名
    private User user;

    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    //返回application/json的数据类型，返回值会序列化位json的字符串
    public Object login (User user, HttpServletRequest req) {
        //模拟用户登录
        if (!"abc".equals(user.getName())) {
            throw new RuntimeException("用户登录失败");
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return user;
    }

    @RequestMapping("/m")
    //返回位String值，返回值位资源的路径（静态资源和服务器资源都可以）
    public String m() {
        return "/main.html";
    }

    @RequestMapping("l1")
    public String l1() {
        //返回路径不带/  以当前路径为相对位置，查找同一级资源
        //带/ 会去掉当前路径，以项目的部署路径为相对位置
        return "forward:login";//以当前请求路径/user/l1，转发到/user/login
        //return "forward:/login";//带/  以项目路径查找/login
    }

    @RequestMapping("l2")
    public String l2() {
        return "redirect:/user/login";
        //return "redirect:login";
        // :后面的/决定了走的路径
    }

    //url为rest 风格的请求
    @RequestMapping("/test/{key}")//路径中使用变量占位符
    @ResponseBody
    public Object Demo1(@PathVariable("key") Integer k) {
        //如果类型不匹配。抛出异常
        System.out.println(test1.get(k));
        return test1;
    }

    //get和post都支持
    @RequestMapping(value = "/Demo2",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    //再浏览器中支持这种的访问方式
    //localhost:8080/user/Demo2?k1=v1&k2=v2&k3=v3
    public Object Demo2(
            //可以匹配url中的参数，请求体k1=v1&k2=v2&k3=v3格式的数据
            @RequestParam("k1") String k1,//写全
            @RequestParam String k2,//省略注解值的方法， 默认以变量为key查找请求数据
            String k3 //最省略的做法，默认就是@RequestParam注解
    ) {
        System.out.println("======" + k1 + ", " + k2 + "," + k3);
        return test1;
    }

    @RequestMapping("/Demo3")
    @ResponseBody
    public Object Demo3(User user) {
        //请求数据与自动映射到参数类型的属性中：name=xx&password=xx
        System.out.println(user);
        return test1;
    }

    @RequestMapping("/Demo4")
    @ResponseBody
    public Object Demo4() {
      return null;//返回为空字符串
    }

    @RequestMapping("/Demo5")
    @ResponseBody
    public Object Demo5() {
        return "ok";//返回字符串，响应数据不再是application/json格式，而是字符串内容
    }

    @RequestMapping("Demo6")
    @ResponseBody
    //http请求是基于Servlet的，Spring已经生成了request和response对象，可以直接在参数中使用
    //Spring自动的将对象注入到参数中
    public Object Demo6(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(req.getParameter("name"));
        System.out.println(req.getParameter("password"));
        return null;
    }

    @RequestMapping("/Demo7")
    @ResponseBody
    public Object Demo7(@RequestBody User user) {
        //请求数据类型为application/json时，反序列化为我们的java对象
        System.out.println("     " + user);
        return null;
    }

    /**
     * web开发经常存在的需求
     * 1 统一处理异常
     * 2 统一返回数据格式
     * 3 统一会话管理（登陆的权限限制）
     * */

    @RequestMapping("/Demo8")
    @ResponseBody
    public Object Demo8() {
        throw new RuntimeException("数据库报错");
    }






}

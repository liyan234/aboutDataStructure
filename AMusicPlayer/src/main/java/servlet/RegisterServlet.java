package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        String userName = req.getParameter("username");
        String userPassword = req.getParameter("passwordOne");
        String checkPassword = req.getParameter("passwordTwo");
        String userAge = req.getParameter("age");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> ret = new HashMap<>();

        if (userName == null || userName.trim().equals("")
        || userPassword == null || userPassword.trim().equals("")
        || userAge == null || userAge.trim().equals("")
        || gender == null || gender.trim().equals("")
        || email == null || email.trim().equals("")) {

            System.out.println("xxxx");
            ret.put("msg",false);
            objectMapper.writeValue(resp.getWriter(),ret);
            return;
        }

        if (!checkPassword.equals(userPassword)) {
            System.out.println("yyy");
            ret.put("msg",false);
            objectMapper.writeValue(resp.getWriter(),ret);
            return;
        }

        if (!gender.equals("男") && !gender.equals("女")) {
            System.out.println("zzz");
            ret.put("msg",false);
            objectMapper.writeValue(resp.getWriter(),ret);
            return;
        }

        if (!checkEmail(email)) {
            System.out.println("vvv");
            ret.put("msg",false);
            objectMapper.writeValue(resp.getWriter(),ret);
            return;
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(userPassword);
        user.setAge(Integer.parseInt(userAge));
        user.setGender(gender);
        user.setEmail(email);

        UserDao userDao = new UserDao();
        userDao.addUser(user);

        ret.put("msg",true);
        objectMapper.writeValue(resp.getWriter(),ret);
    }

    private boolean checkEmail(String email) {
        boolean rs = false;

        //邮件的正则表达式
        String regEx =  "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        // 编译正则表达式
        //Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(email);
        // 字符串是否与正则表达式相匹配
        rs = matcher.matches();
        System.out.println(rs);

        return rs;
    }
}

package servlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class check {

    public static void main(String[] args) {

        String str = "675672214.com";

        //邮件的正则表达式
        String regEx =  "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        // 编译正则表达式
        //Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        System.out.println(rs);
    }
}

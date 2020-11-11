package view;

public class HtmlGenerator {
    public static String getMessage(String message, String nextUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");

        builder.append("<head>");
        builder.append("<meta charset=\"utf-8\">");
        builder.append("<title>提示页面</title>");
        builder.append("</head>");

        builder.append("<body>");

        builder.append("<h3>");
        builder.append(message);
        builder.append("</h3>");
        builder.append(String.format("<a href=\"%s\"> 点击这里进行跳转 </a>", nextUrl));
        builder.append("</body>");

        builder.append("</html>");
        return builder.toString();
    }
}
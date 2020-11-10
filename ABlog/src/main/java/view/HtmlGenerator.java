package view;

import model.Article;
import model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//用字符串拼接的模式，构建一个页面
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
        builder.append(String.format("<a href=\"%s\"> 点击这里进行跳转 </a>",nextUrl));
        builder.append("</body>");

        builder.append("</html>");
        return builder.toString();
    }


    public static String getArticlePage(Article article, User user, User author) {
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");
        builder.append("<head>");
        builder.append("<meta charset=\"utf-8\">");
        builder.append("<title>文章详情</title>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<h3>欢迎你" + user.getName() + "</h3>");
        builder.append("<hr>");
        builder.append(String.format("<h1>%s</h1>", article.getTitle()));
        builder.append(String.format("<h4>%s</h4>", author.getName()));
        //构造正文 ,/n 换成<br>
        builder.append(String.format("<div>%s</div>",article.getContent().
                replace("\n","<br>")));
        builder.append("</body>");
        builder.append("</html>");

        return builder.toString();
    }

    public static String getArticleListPage(List<Article> articles, User user) {

        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<meta charset=\"utf-8\">");
        builder.append("<title>所有文章</title>");
        builder.append("</head>");

        builder.append("<body>");
        builder.append("<h3>欢迎你" + user.getName() + "</h3>");

        builder.append("<hr>");
        for (Article article : articles) {
            builder.append(String.format("<a class =\"article\" href=\"article?articleId=%d\"> %s <a>"
                    + "<a href=\"deleteArticle?articleId=%d\"> 删除 </a>",
                    article.getArticleId(), article.getTitle(), article.getArticleId()));
            builder.append("<br>");
        }

        builder.append("<hr>");
        builder.append(String.format("<div>当前共有博客%d篇<div>", articles.size()));

        //新增博客
        builder.append("<div>发布文章</div>");

        builder.append("<div>");

        builder.append("<form method=\"post\" action=\"article\">");
        builder.append("<input type=\"text\" name=\"title\" placeholder=\"请输入标题\">");
        builder.append("<br>");
        builder.append("<textarea name=\"content\" placeholder=\"请输入内容\" style=\"width: 500px; height: 300px;\"> </textarea>");
        builder.append("<br>");
        builder.append("<input type=\"submit\" value=\"发布文章\">");
        builder.append("</form>");

        builder.append("</div>");

        builder.append("</body>");
        builder.append("</html>");

        return builder.toString();
    }
}

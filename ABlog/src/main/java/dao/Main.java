package dao;

import model.Article;
import model.User;

import java.util.List;

public class Main {


    public static void main(String[] args) {
        /*User user = new User();
        user.setPassWord("123");
        user.setName("www");

        UserDao userDao = new UserDao();
        //userDao.addUser(user);

        User user1 = userDao.selectUserById(1);
        User user2 = userDao.selectUserByName("www");
        System.out.println(user1);
        System.out.println(user2);*/

        Article article = new Article();
        article.setTitle("wenzhang1");
        article.setContent("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        article.setUserId(1);
        ArticleDao articleDao = new ArticleDao();
        //articleDao.addArticle(article);
        //List<Article> articles = articleDao.selectAll();
        //System.out.println(articles);
        //Article article1 = articleDao.selectArticleByArticleId(1);
        //System.out.println(article1);
        articleDao.deleteArticleById(1);
    }
}

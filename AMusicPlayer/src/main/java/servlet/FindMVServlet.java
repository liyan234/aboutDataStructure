package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MVDao;
import model.MV;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindMVServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        ObjectMapper mapper = new ObjectMapper();
        List<MV> list = new ArrayList<>();

        String mvName = req.getParameter("MVName");
        MVDao dao = new MVDao();

        if (mvName == null) {
            list = dao.selectAllMV();

        } else {
            list = dao.selectMVByKey(mvName);
        }
        System.out.println(list);
        mapper.writeValue(resp.getWriter(), list);
    }
}

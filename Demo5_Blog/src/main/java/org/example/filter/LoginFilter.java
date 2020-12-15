package org.example.filter;

import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

// 匹配路径 指的是匹配所有方法路径
// 进行会话管理
/**
 * 配置用户统一会话管理的过滤器，配配置有请求路径
 * 服务器 资源： /login 不用session检验 其他都需要 返回401
 * 前端资源： /jsp/校验Session ，不通过重定向到登录页面
 *           /js/, /static/, /view/ 全都不校验
* */

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 每次http请求匹配到过滤器路径时，会执行过滤器的doFilter
     * 如果往下执行，是调用FilterChain.doFilter(request, response)
     * 否则自行处理响应
     * */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String servletPath = req.getServletPath();
        // 不需要登录允许访问：
        if (servletPath.startsWith("/js/") || servletPath.startsWith("/static/")
        || servletPath.startsWith("/view/") || servletPath.equals("/login")) {
            chain.doFilter(request, response);
        } else {
            // 获取session对象
            HttpSession session = req.getSession();
            // 验证用户是否登录 ， 如果没有登录， hi需要根据前端或后端做不同的处理
            if (session == null || session.getAttribute("user") == null) {
                // 前端重定向到登录页面
                // 后端返回401状态码

                if (servletPath.startsWith("/jsp/")) {
                    // 重定向到登陆页面
                    // 绝对路径
                    resp.sendRedirect(basePath(req) + "/view/login.html");

                } else {
                    // 401 状态码
                    resp.setStatus(401);
                    resp.setContentType("application/json; charset=UTF-8");
                    JSONResponse jsonResponse = new JSONResponse();
                    jsonResponse.setCode("NOLOGIN");
                    jsonResponse.setMessage("用户没有登录");
                    PrintWriter pw = resp.getWriter();
                    // 序列化
                    pw.println(JSONUtil.serialize(jsonResponse));
                    pw.flush();
                    pw.close();
                }

            } else {
                chain.doFilter(request, response);
            }
        }
    }

    /**
     * 根据http请求，动态获取访问路径
     * */
    public static String basePath(HttpServletRequest request) {
        // http
        String schema = request.getScheme();
        // 主机ip或域名
        String host = request.getServerName();
        // 端口
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        return schema + "://" + host + ":" + port + contextPath;
    }

    @Override
    public void destroy() {

    }
}

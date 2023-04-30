package com.iweb.d0429_springboot_shop.filter;

import com.iweb.d0429_springboot_shop.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yang
 * @date 2023/4/29 13:19
 */
@Component
@WebFilter("/*")
public class C_ForeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if(uri.startsWith("/admin")||uri.endsWith("login")||uri.endsWith("gif")||uri.endsWith("login.jsp")||uri.endsWith("register.jsp")
                ||uri.endsWith("show")|| uri.endsWith("register")||uri.endsWith(".png")||uri.endsWith("jpg")||uri.startsWith("/page/fore/noLogin")
                ||uri.endsWith("List")||uri.endsWith("list") || uri.endsWith("css")||uri.endsWith("js")||uri.startsWith("/page/admin")){
            filterChain.doFilter(req,resp);
            return;
        }
        // 我们这里省略cookie的操作 我们假设 登录成功 就直接把用户名存放在当前session中
        // 这里过滤器就应该从session中获取用户名 判断是否为空 来判断用户是否成功登录
        User user = (User) req.getSession().getAttribute("foreUser");
        if (null==user){
            resp.sendRedirect("/page/fore/noLogin/login.jsp");
            return;
        }
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}

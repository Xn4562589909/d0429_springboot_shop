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
 * @date 2023/4/29 13:06
 */
@Component
@WebFilter("/*")
public class B_AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if(uri.startsWith("/fore")||uri.endsWith("login.jsp")||uri.endsWith("login")||uri.endsWith("register")
                ||uri.endsWith("gif")||uri.endsWith(".png")||uri.endsWith("register.jsp")
                ||uri.endsWith("jpg")||uri.endsWith("css")||uri.endsWith("js")||uri.startsWith("/page/fore")){
            filterChain.doFilter(req,resp);
            return;
        }
        User user = (User) req.getSession().getAttribute("user");
        if (null == user){
            resp.sendRedirect("/page/admin/user/login.jsp");
            return;
        }
        filterChain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}

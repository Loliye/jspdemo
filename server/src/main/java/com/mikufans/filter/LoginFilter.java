package com.mikufans.filter;


import com.mikufans.bean.User;
import com.mikufans.constant.SSOConstants;
import com.mikufans.utlls.HttpUtil;
import net.sf.json.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = {"/IndexServlet"})
public class LoginFilter implements Filter
{
    public void destroy()
    {
        System.out.println("LoginFilter destroy ...");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
    {
        System.out.println("LoginFilter doFilter ...");

        //拦截未登录
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getSession().getAttribute("user") == null)
        {

            System.out.println("未登录拦截：" + request.getRequestURI());
            System.out.println("filter:" + request.getRequestURL());
            response.sendRedirect("/LoginServlet");
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException
    {
        System.out.println("LoginFilter init ...");
    }

}

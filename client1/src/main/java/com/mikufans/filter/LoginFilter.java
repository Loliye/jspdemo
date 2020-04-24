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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = "/IndexServlet")
public class LoginFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        System.out.println("client1 loginFilter init....");
    }

    public void gotoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String url = request.getRequestURL().toString();
        System.out.println("url:" + url);
        response.sendRedirect(SSOConstants.SSO_LOGIN_URL + "?backUrl=" + URLDecoder.decode(url,"UTF-8"));
        System.out.println("urlDecode:"+ URLDecoder.decode(url,"UTF-8"));
        System.out.println("backUrl:" + URLEncoder.encode(url, "UTF-8"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        //拦截未登录，登录页面除外
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession().getAttribute("user") == null)
        {
            System.out.println("未登录拦截：" + request.getRequestURI());
            String ssoToken = request.getParameter("token");
            if (ssoToken == null || ssoToken.length() == 0)
            {
                gotoLogin(request, response);
                return;
            }
            System.out.println("token:" + ssoToken);

            //校验token
            JSONObject result = HttpUtil.doGet(SSOConstants.SSO_LOGIN_TOKEN_VERIFY + "?token=" + ssoToken);
            if (result == null)
            {
                gotoLogin(request, response);
                return;
            }

            String code = (String) result.get("code");
            if (code == null || code.length() == 0)
            {
                System.out.println("校验token失败：" + result.get("msg"));
                gotoLogin(request, response);
                return;
            }

            System.out.println("校验token成功！自动登陆");
            //应该通过redis查看对象token是否存活
            User user = getUser();

            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("SSO_TOKEN", ssoToken);
            ServletContext application = request.getSession().getServletContext();
            Map<String, String> ssoTokenMap = (Map<String, String>) application.getAttribute("SSO_TOKEN_MAP");
            if (ssoTokenMap == null)
                ssoTokenMap = new HashMap<>();
            ssoTokenMap.put(ssoToken, request.getSession().getId());
            application.setAttribute("SSO_TOKEN_MAP", ssoTokenMap);
        }
        filterChain.doFilter(request, response);
    }

    public User getUser()
    {
        return new User(1, "miku", 1, 17);
    }


    @Override
    public void destroy()
    {
        System.out.println("client1 loginFilter destroy....");
    }
}

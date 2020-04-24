package com.mikufans.web;


import com.mikufans.constant.SSOConstants;
import com.mikufans.utlls.HttpUtil;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("退出登录");

        //通知SSO系统，注销对应token的登录用户
        String SSO_TOKEN = (String) request.getSession().getAttribute("SSO_TOKEN");
        HttpUtil.doGet(SSOConstants.SSO_LOGOUT_URL + "?SSO_TOKEN=" + SSO_TOKEN);

        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("SSO_TOKEN", null);

        response.sendRedirect("/IndexServlet");
        return;
    }
}

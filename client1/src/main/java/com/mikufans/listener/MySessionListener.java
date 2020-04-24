package com.mikufans.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener
{

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        MySessionContext.addSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {
        HttpSession session = httpSessionEvent.getSession();
        MySessionContext.delSession(session);
    }

}

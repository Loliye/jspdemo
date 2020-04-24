package com.mikufans.bean;



public class RestApi<T>
{
    public T data;
    public int code;
    public String msg;


    public RestApi(T data, int code, String msg)
    {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}

package com.mikufans.bean;

public class User
{

    private Integer id;
    private String name;
    private Integer sex;
    private Integer age;

    public User(Integer id, String name, Integer sex, Integer age)
    {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }
}

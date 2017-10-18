package com.kangning.server.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by kieren on 17/10/18.
 */
@Component
public class User implements Serializable{
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

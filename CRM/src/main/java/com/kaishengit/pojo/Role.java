package com.kaishengit.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {

    private Integer id;
    private String rolename;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                '}';
    }
}

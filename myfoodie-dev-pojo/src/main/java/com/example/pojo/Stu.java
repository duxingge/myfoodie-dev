package com.example.pojo;

import javax.persistence.Column;
import javax.persistence.Id;

public class Stu {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }
}
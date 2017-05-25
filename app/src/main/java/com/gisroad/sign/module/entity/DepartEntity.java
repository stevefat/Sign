package com.gisroad.sign.module.entity;
/**
 * Created by stevefat on 17-5-11.
 */

import org.litepal.crud.DataSupport;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午5:15
 */
public class DepartEntity extends DataSupport {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

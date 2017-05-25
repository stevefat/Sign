package com.gisroad.sign.module.entity;
/**
 * Created by stevefat on 17-5-11.
 */

import org.litepal.crud.DataSupport;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午5:17
 */
public class DepartUserEntity extends DataSupport {
    private int id;
    private String name;
    private String url;
    private int did;

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

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

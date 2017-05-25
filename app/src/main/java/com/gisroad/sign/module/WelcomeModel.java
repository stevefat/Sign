package com.gisroad.sign.module;
/**
 * Created by stevefat on 17-5-11.
 */

import com.gisroad.sign.module.entity.DepartEntity;
import com.gisroad.sign.module.entity.DepartUserEntity;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-11 下午2:47
 */
public class WelcomeModel {

    public void saxHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("a.zzTree_0");
        int j = 0;
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String href = element.attr("href");
            //存储部门
            if (href.indexOf("javascript:") != -1) {
                DepartEntity departEntity = new DepartEntity();
                String name = element.text();
                departEntity.setName(name);
                j = ++j;
                departEntity.setId(j);
                departEntity.save();
            } else {
                //部门的人员
                String name = element.text();
                String url = href.substring(href.indexOf("=") + 1);
                DepartUserEntity userEntity = new DepartUserEntity();
                userEntity.setName(name);
                userEntity.setUrl(url);
                userEntity.setDid(j);
                userEntity.save();

            }

        }
        EventBus.getDefault().post("success");
    }
}

package com.gisroad.sign.util;
/**
 * Created by stevefat on 17-5-23.
 */

import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-23 上午11:18
 */
public class ShareSdkUtil {

    public static void showShare(Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("众智签到查询");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("https://fir.im/8tdf");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("分享查询签到的App，杜绝未签到!");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://oqe90zrpq.bkt.clouddn.com/ic_launcher.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://fir.im/8tdf");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("欢迎使用这个签到查询app");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://fir.im/8tdf");
        // 启动分享GUI
        oks.show(context);
    }
}

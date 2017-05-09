package com.dqr.www.textimagepager.textimagepager;

import java.io.Serializable;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-04-22 15:16
 */

public class ImagePagerBean implements Serializable {
    private String mUrl;
    private String mDesc;
    private String mSmallUrl;


    public ImagePagerBean(String url, String desc, String smallUrl) {
        mUrl = url;
        mDesc = desc;
        this.mSmallUrl=smallUrl;
    }


    public String getSmallUrl() {
        return mSmallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        mSmallUrl = smallUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }
}

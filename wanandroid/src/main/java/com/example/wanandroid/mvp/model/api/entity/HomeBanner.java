package com.example.wanandroid.mvp.model.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeBanner implements Parcelable {
    /**
     * desc : 享学~
     * id : 29
     * imagePath : https://www.wanandroid.com/blogimgs/8e95ad05-a6f5-4c65-8a89-f8d4b819aa80.jpeg
     * isVisible : 1
     * order : 0
     * title : 做了5年Android，靠这份面试题和答案从12K涨到30K
     * type : 0
     * url : https://mp.weixin.qq.com/s/oxoocfuPBS-fYI1Y0HU5QQ
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    public String getDesc() { return desc;}

    public void setDesc(String desc) { this.desc = desc;}

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getImagePath() { return imagePath;}

    public void setImagePath(String imagePath) { this.imagePath = imagePath;}

    public int getIsVisible() { return isVisible;}

    public void setIsVisible(int isVisible) { this.isVisible = isVisible;}

    public int getOrder() { return order;}

    public void setOrder(int order) { this.order = order;}

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public int getType() { return type;}

    public void setType(int type) { this.type = type;}

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeInt(this.id);
        dest.writeString(this.imagePath);
        dest.writeInt(this.isVisible);
        dest.writeInt(this.order);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeString(this.url);
    }

    public HomeBanner() {}

    protected HomeBanner(Parcel in) {
        this.desc = in.readString();
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.isVisible = in.readInt();
        this.order = in.readInt();
        this.title = in.readString();
        this.type = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<HomeBanner> CREATOR = new Parcelable.Creator<HomeBanner>() {
        @Override
        public HomeBanner createFromParcel(Parcel source) {return new HomeBanner(source);}

        @Override
        public HomeBanner[] newArray(int size) {return new HomeBanner[size];}
    };
}


package com.example.jeonsang_gyu.test1.Post;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Poster implements Parcelable,Serializable {


    private String Title;
    private String Desc;
    private String Due;
    private String ImagePath;
    private String Location;
    private String Host;



    public Poster()
    {
        this.Title = "";
        this.Desc = "";
        this.Due = "";
        this.Host="";
        this.Location="";
        this.ImagePath="";



    }


    public Poster(String title, String desc, String due, String location, String Host) {
        this.Title = title;
        this.Desc = desc;
        this.Due = due;
        this.Host=Host;
        this.Location=location;

        this.ImagePath="";
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setDue(String due) {
        Due = due;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setHost(String host) {
        Host = host;
    }

    public void setLocation(String location) {

        Location = location;
    }



    public String getTitle() {

        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public String getDue() {
        return Due;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getHost() {
        return Host;
    }


    public String getLocation() {
        return Location;
    }



    //이거 writeToParcel이랑 순서 같이 해야 한다
    protected Poster(Parcel in) {
        Title = in.readString();
        Desc = in.readString();
        Due = in.readString();
       ImagePath=in.readString();
        Host = in.readString();
        Location=in.readString();
    }

    public static final Creator<Poster> CREATOR = new Creator<Poster>() {
        @Override
        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        @Override
        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(Desc);
        parcel.writeString(Due);
        parcel.writeString(ImagePath);
        parcel.writeString(Host);
        parcel.writeString(Location);



    }
}

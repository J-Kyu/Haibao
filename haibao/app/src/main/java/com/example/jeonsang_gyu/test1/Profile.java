package com.example.jeonsang_gyu.test1;


public class Profile {
    private String Identifier;
    private String ImagePath;
    private String Major;
    private String Name;
    private String UID;





    public Profile() {
        this.Identifier = " ";
        this.ImagePath=" ";
        this.Major = " "  ;
        this.Name = " ";
        this.UID="" ;

    }


    public Profile(String a, String b, String c, String d,String e) {
        this.Identifier = a;
        this.ImagePath=b;
        this.Major = c;
        this.Name = d;
        this.UID=e;

    }

    public String getImagePath() {
        return this.ImagePath;
    }

    public void setImagePath(String imagePath) {
        this.ImagePath = imagePath;
    }

    public String getIdentifier() {
        return this.Identifier;
    }

    public void setIdentifier(String ID) {
        this.Identifier = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
